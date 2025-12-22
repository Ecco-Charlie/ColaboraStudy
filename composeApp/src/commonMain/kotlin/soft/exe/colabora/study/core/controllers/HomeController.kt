package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.UserData
import soft.exe.colabora.study.core.service.UserDataService
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.ui.navigation.Login
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class HomeController(private val udService: UserDataService) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent = _navEvent.receiveAsFlow()

    var userData: UserData? = null
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
    val hours: StateFlow<Int> = _hours

    fun onChangeHours(value: Int) {
        this._hours.value = value
    }

    private val _minutes = MutableStateFlow(0)
    val minutes: StateFlow<Int> = _minutes

    fun onChangeMinutes(value: Int) {
        this._minutes.value = value
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

}