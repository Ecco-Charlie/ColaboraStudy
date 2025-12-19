package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.UserDataService
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.ui.navigation.Login
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class HomeController(private val udService: UserDataService) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent = _navEvent.consumeAsFlow()

    init {
        viewModelScope.launch {
            val ud = udService.instance
            if (ud == null)
                _navEvent.send(NavigationEvent.NavigateTo(Login))
            else
                _load.value = LoadState.Ok
        }
    }

}