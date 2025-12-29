package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.Question
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.utils.LoadState

class ExamController(private val connectionClient: ConnectionClient) : ViewModel() {

    private val _load = MutableStateFlow<LoadState>(LoadState.Load)
    val load: StateFlow<LoadState> = _load

    private val _question = MutableStateFlow<Question?>(null)
    val question: StateFlow<Question?> = _question

    val numOfQuestions: Int = this.connectionClient.numOfQuestions()

    init {
        viewModelScope.launch {
            nextQuestion()
        }
    }

    suspend fun nextQuestion() {
        this._question.value = this.connectionClient.nextQuestion()
    }

}