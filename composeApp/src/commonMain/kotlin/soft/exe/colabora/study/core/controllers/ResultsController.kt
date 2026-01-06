package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.messages.ExamResults
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.ui.navigation.Home
import soft.exe.colabora.study.ui.navigation.NavigationEvent
import soft.exe.colabora.study.ui.navigation.ResultsServer

class ResultsController(
    private val connectionClient: ConnectionClient,
    private val connectionService: ConnectionService
) : ViewModel() {

    val results: StateFlow<ExamResults?> = connectionClient.results

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent: Flow<NavigationEvent> = _navEvent.receiveAsFlow()

    fun closeConnection() {
        viewModelScope.launch {
            if (connectionService.isRunning) {
                _navEvent.send(NavigationEvent.NavigateToAndClear(ResultsServer))
            } else {
                _navEvent.send(NavigationEvent.NavigateToAndClear(Home))
            }
        }
    }

}