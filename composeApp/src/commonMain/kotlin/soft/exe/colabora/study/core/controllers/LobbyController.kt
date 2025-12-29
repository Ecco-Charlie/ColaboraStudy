package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.ui.navigation.Exam
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class LobbyController(
    private val questionsService: QuestionsService,
    private val connectionService: ConnectionService,
    private val connectionClient: ConnectionClient
) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    val players: StateFlow<List<Player>> = connectionService.players

    private val _participate = MutableStateFlow(false)
    val participate: StateFlow<Boolean> = _participate

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent = _navEvent.receiveAsFlow()

    fun changeParticipation() {
        this._participate.value = !this._participate.value
        if (this._participate.value) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    connectionClient.connectToServer("127.0.0.1")
                }
            }
        } else {
            viewModelScope.launch {
                connectionClient.closeConnection()
            }
        }
    }

    init {
        viewModelScope.launch {
            questionsService.questions.collect { questions ->
                if (questions.isEmpty())
                    return@collect
                _load.value = LoadState.Ok
            }
        }
        viewModelScope.launch {
            connectionService.startServer()
        }
    }

    fun startGame() {
        val numOfQuestions = this.questionsService.questions.value.size
        if (this.players.value.isEmpty() || numOfQuestions == 0)
            return
        viewModelScope.launch {
            connectionService.startGame(numOfQuestions)
        }
        viewModelScope.launch {
            if (_participate.value) {
                _navEvent.send(NavigationEvent.NavigateToAndClear(Exam))
            } else {
                TODO()
            }
        }
    }

}