package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.utils.LoadState

class LobbyController(
    private val questionsService: QuestionsService,
    private val connectionService: ConnectionService
) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    val players: StateFlow<List<Player>> = connectionService.players

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

}