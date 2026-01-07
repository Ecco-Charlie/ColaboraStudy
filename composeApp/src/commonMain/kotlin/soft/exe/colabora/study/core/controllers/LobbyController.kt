package soft.exe.colabora.study.core.controllers

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.exam_cannot_have_0
import colaborastudy.composeapp.generated.resources.questions_no_yet_load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString
import soft.exe.colabora.study.core.entity.ServerPlayer
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.ui.navigation.Exam
import soft.exe.colabora.study.ui.navigation.NavigationEvent
import soft.exe.colabora.study.ui.navigation.ResultsServer

class LobbyController(
    private val questionsService: QuestionsService,
    private val connectionService: ConnectionService,
    private val connectionClient: ConnectionClient
) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    val players: StateFlow<List<ServerPlayer>> = connectionService.players

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

    val snackState = SnackbarHostState()

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
        if (numOfQuestions == 0) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.questions_no_yet_load))
            }
            return
        }
        if (this.players.value.isEmpty()) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.exam_cannot_have_0))
            }
            return
        }
        viewModelScope.launch {
            connectionService.startGame(numOfQuestions, questionsService.totalTimeInSeconds)
        }
        viewModelScope.launch {
            if (_participate.value) {
                _navEvent.send(NavigationEvent.NavigateToAndClear(Exam))
            } else {
                _navEvent.send(NavigationEvent.NavigateToAndClear(ResultsServer))
            }
        }
    }

}