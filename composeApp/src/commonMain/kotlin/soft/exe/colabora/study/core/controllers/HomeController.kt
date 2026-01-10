package soft.exe.colabora.study.core.controllers

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.description_not_empty
import colaborastudy.composeapp.generated.resources.error_while_questions
import colaborastudy.composeapp.generated.resources.fill_ip
import colaborastudy.composeapp.generated.resources.no_api_key
import colaborastudy.composeapp.generated.resources.no_file_selected
import colaborastudy.composeapp.generated.resources.no_possible_connect_exam
import colaborastudy.composeapp.generated.resources.time_cannot_less_60
import colaborastudy.composeapp.generated.resources.wait_start
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.util.encodeToByteArray
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
import soft.exe.colabora.study.BuildKonfig
import soft.exe.colabora.study.core.entity.PromptParameters
import soft.exe.colabora.study.core.entity.UserData
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.service.UserDataService
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.core.utils.getIp
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

    val snackState = SnackbarHostState()

    private val _selectedClstFile = MutableStateFlow<PlatformFile?>(null)
    val selectedClstFile: StateFlow<PlatformFile?> = _selectedClstFile

    fun selectClstFile() {
        viewModelScope.launch {
            val file = FileKit.openFilePicker(type = FileKitType.File(extension = "clst"))
            _selectedClstFile.value = file
        }
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
        if (BuildKonfig.GEMINI_API_KEY == null) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.no_api_key))
            }
            return
        }
        val timeInSeconds = getTimeInSeconds()
        if (this._description.value.isEmpty() || this._description.value.length < 10) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.description_not_empty))
            }
            return
        }
        if (timeInSeconds < 60) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.time_cannot_less_60))
            }
            return
        }
        this._load.value = LoadState.Load
        viewModelScope.launch {
            val qs = KoinPlatform.getKoin().get<QuestionsService>()
            qs.setPromptParameters(PromptParameters(
                numOfQuestions = _numOfQuestions.value.toInt(),
                description = _description.value,
                difficulty = _difficulty.value.toInt(),
                totalTime = timeInSeconds,
                referencePhoto = _photo.value?.encodeToByteArray()
            ))
            try {
                this.launch {
                    qs.loadQuestions()
                }
                _navEvent.send(NavigationEvent.NavigateTo(Lobby))
            } catch(_: Exception) {
                snackState.showSnackbar(getString(Res.string.error_while_questions))
            }
        }
    }

    private fun getTimeInSeconds(): Int {
        return (_minutes.value * 60) + (_hours.value * 3600)
    }

    fun connectToExam() {
        viewModelScope.launch {
            if (_ip.value.isEmpty()) {
                snackState.showSnackbar(getString(Res.string.fill_ip))
                return@launch
            }
            _load.value = LoadState.Load
            try {
                withContext(Dispatchers.IO) {
                    val ipC = getIp(_ip.value.toInt())
                    require(ipC != null)
                    KoinPlatform.getKoin().get<ConnectionClient>().connectToServer(ipC)
                }
                _navEvent.send(NavigationEvent.NavigateToAndClear(Wait(text = getString(Res.string.wait_start))))
            } catch (_: Exception) {
                _load.value = LoadState.Ok
                snackState.showSnackbar(getString(Res.string.no_possible_connect_exam))
            }
        }
    }

    fun startGameWithFile() {
        if (_selectedClstFile.value == null) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.no_file_selected))
            }
            return
        }
        val timeInSeconds = getTimeInSeconds()
        if (timeInSeconds < 60) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.time_cannot_less_60))
            }
            return
        }
        viewModelScope.launch {
            _navEvent.send(NavigationEvent.NavigateTo(Lobby))
            val qs = KoinPlatform.getKoin().get<QuestionsService>()
            qs.loadQuestionsFile(_selectedClstFile.value!!)
            qs.setTime(timeInSeconds)
        }
    }

    fun removePhoto() {
        this._photo.value = null
    }

}