package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.Question
import soft.exe.colabora.study.core.service.ConnectionClient

class ExamController(private val connectionClient: ConnectionClient) : ViewModel() {

    val numOfQuestions: Int = this.connectionClient.numOfQuestions()

    val currentQuestion: StateFlow<Question?> = connectionClient.currentQuestion

    private val _selectedAnswer = MutableStateFlow<Int?>(null)
    val selectedAnswer: StateFlow<Int?> = _selectedAnswer

    fun changeSelectedAnswer(value: Int) {
        this._selectedAnswer.value = value
    }

    init {
        viewModelScope.launch {
            connectionClient.nextQuestion()
            connectionClient.finish.collect {
                TODO()
            }
        }
    }

    fun nextQuestion() {
        if (this.currentQuestion.value == null || this._selectedAnswer.value == null)
            return
        this.connectionClient.registerQuestionAnswer(
            questionId = this.currentQuestion.value!!.id,
            answerId = this._selectedAnswer.value!!
        )
        viewModelScope.launch {
            connectionClient.nextQuestion()
        }
    }

}