package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.ServerPlayer
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.ui.navigation.Home
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class ResultsServerController(private val connectionService: ConnectionService) : ViewModel() {

    val players: StateFlow<List<ServerPlayer>> = connectionService.players

    private val _allFinished = MutableStateFlow(false)
    val allFinished: StateFlow<Boolean> = _allFinished

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent: Flow<NavigationEvent> = _navEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            players.collect { ps ->
                val i = ps.indexOfFirst { !it.finished }
                if (i == -1) {
                    _allFinished.value = true
                }
            }
        }
    }

    fun finishExam() {
        viewModelScope.launch {
            _navEvent.send(NavigationEvent.NavigateToAndClear(Home))
        }
        connectionService.finishGame()
    }

}