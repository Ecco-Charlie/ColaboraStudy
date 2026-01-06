package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.ServerPlayer
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.ui.navigation.Home
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class ResultsServerController(private val connectionService: ConnectionService) : ViewModel() {

    val players: StateFlow<List<ServerPlayer>> = connectionService.players

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent: Flow<NavigationEvent> = _navEvent.receiveAsFlow()

    fun finishExam() {
        viewModelScope.launch {
            _navEvent.send(NavigationEvent.NavigateToAndClear(Home))
        }
        connectionService.finishGame()
    }

}