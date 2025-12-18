package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.ui.navigation.Login
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class HomeController : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent = _navEvent.consumeAsFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _navEvent.send(NavigationEvent.NavigateTo(Login))
        }
    }

}