package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.ui.navigation.Exam
import soft.exe.colabora.study.ui.navigation.Home
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class WaitController(connectionClient: ConnectionClient) : ViewModel() {

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent: Flow<NavigationEvent> = _navEvent.receiveAsFlow()

    var finish: Flow<Boolean> = connectionClient.finish
    var start: Flow<Boolean> = connectionClient.start

    val finishScope: CoroutineScope = viewModelScope
    val startScope: CoroutineScope = viewModelScope

    init {
        startScope.launch {
            start.zip(start.drop(1)) { _, _ -> }.collect {
                _navEvent.send(NavigationEvent.NavigateToAndClear(Exam))
                startScope.cancel()
                finishScope.cancel()
            }
        }
        finishScope.launch {
            finish.zip(finish.drop(1)){ _, _ -> }.collect {
                _navEvent.send(NavigationEvent.NavigateToAndClear(Home))
                startScope.cancel()
                finishScope.cancel()
            }
        }
    }

}