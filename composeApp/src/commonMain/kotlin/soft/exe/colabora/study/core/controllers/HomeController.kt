package soft.exe.colabora.study.core.controllers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.base_prompt
import colaborastudy.composeapp.generated.resources.text_image_prompt
import colaborastudy.composeapp.generated.resources.text_prompt
import colaborastudy.composeapp.generated.resources.wait_start
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.util.toImageBitmap
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString
import org.koin.mp.KoinPlatform
import soft.exe.colabora.study.core.entity.UserData
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.service.UserDataService
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.ui.navigation.Lobby
import soft.exe.colabora.study.ui.navigation.Login
import soft.exe.colabora.study.ui.navigation.NavigationEvent
import soft.exe.colabora.study.ui.navigation.Wait

class HomeController(
    private val udService: UserDataService
) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent = _navEvent.receiveAsFlow()

    var userData by mutableStateOf<UserData?>(null)
        private set

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    fun onDescriptionChange(value: String) {
        this._description.value = value
    }

    private val _numOfQuestions = MutableStateFlow(5f)
    val numOfQuestions: StateFlow<Float> = _numOfQuestions

    fun onChangeNumOfQuestions(value: Float) {
        this._numOfQuestions.value = value
    }

    private val _difficulty = MutableStateFlow(0f)
    val difficulty: StateFlow<Float> = _difficulty

    fun onChangeDifficulty(value: Float) {
        this._difficulty.value = value
    }

    private val _hours = MutableStateFlow(0)

    fun onChangeHours(value: Int) {
        this._hours.value = value
    }

    private val _minutes = MutableStateFlow(0)

    fun onChangeMinutes(value: Int) {
        this._minutes.value = value
    }

    private val _photo = MutableStateFlow<ImageBitmap?>(null)
    val photo: StateFlow<ImageBitmap?> = _photo

    fun changePhoto() {
        viewModelScope.launch {
            val res = FileKit.openFilePicker(type = FileKitType.Image) ?: return@launch
            _photo.value = res.toImageBitmap()
        }
    }

    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip

    fun onChangeIp(value: String) {
        this._ip.value = value
    }

    init {
        viewModelScope.launch {
            val ud = udService.instance
            if (ud == null)
                _navEvent.send(NavigationEvent.NavigateToAndClear(Login))
            else {
                udService.loadPicture()
                userData = ud
                _load.value = LoadState.Ok
            }
        }
    }

    fun generateQuestions() {
        this._load.value = LoadState.Load
        val timeInSeconds = (_minutes.value * 60) + (_hours.value * 3600)
        if (this._description.value.isEmpty() || timeInSeconds < 60)
            return
        val type = if (this._photo.value != null) Res.string.text_image_prompt
                    else Res.string.text_prompt
        viewModelScope.launch {
            val prompt = getString(
                Res.string.base_prompt,
                _numOfQuestions.value.toInt(),
                _description.value,
                _difficulty.value.toInt(),
                ((_minutes.value+(_hours.value*60)) / _numOfQuestions.value.toInt()),
                getString(type)
            )
            _navEvent.send(NavigationEvent.NavigateTo(Lobby))
            val qs = KoinPlatform.getKoin().get<QuestionsService>()
            qs.loadQuestions(prompt)
            qs.setTime(timeInSeconds)
        }
    }

    fun connectToExam() {
        this._load.value = LoadState.Load
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    KoinPlatform.getKoin().get<ConnectionClient>().connectToServer(_ip.value)
                }
                _navEvent.send(NavigationEvent.NavigateToAndClear(Wait(text = getString(Res.string.wait_start))))
            } catch (_: Exception) {
                _load.value = LoadState.Ok
            }
        }
    }

}