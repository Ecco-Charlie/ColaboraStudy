package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.waiting_exam_end
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import soft.exe.colabora.study.core.entity.messages.ExamResults
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.ui.navigation.NavigationEvent
import soft.exe.colabora.study.ui.navigation.ResultsServer
import soft.exe.colabora.study.ui.navigation.Wait

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
                _navEvent.send(NavigationEvent.NavigateToAndClear(Wait(text = getString(Res.string.waiting_exam_end))))
            }
        }
    }

}