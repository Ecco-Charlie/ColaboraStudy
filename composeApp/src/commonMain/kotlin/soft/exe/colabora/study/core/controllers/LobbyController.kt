package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.utils.LoadState

class LobbyController(
    private val questionsService: QuestionsService,
    private val connections: ConnectionService
) : ViewModel() {

    private val questionScope = CoroutineScope(Dispatchers.Default)

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    init {
        questionScope.launch {
            questionsService.questions.collect { questions ->
                if (questions.isNotEmpty())
                    _load.value = LoadState.Ok
            }
        }

        viewModelScope.launch {
            connections.startServer()
        }
    }

}