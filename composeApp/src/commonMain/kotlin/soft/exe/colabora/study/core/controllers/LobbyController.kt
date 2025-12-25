package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.utils.LoadState

class LobbyController(private val questionsService: QuestionsService) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    init {
        viewModelScope.launch {
            questionsService.questions.collect { questions ->
                if (questions.isEmpty())
                    return@collect
                _load.value = LoadState.Ok
            }
        }
    }

}