package soft.exe.colabora.study.core.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import soft.exe.colabora.study.core.entity.Answer
import soft.exe.colabora.study.core.entity.Question

class QuestionsService {

    private val _questions = MutableStateFlow<List<Question>>(listOf())
    val questions: StateFlow<List<Question>> = _questions

    suspend fun loadQuestions(prompt: String) {
        delay(5000)
        this._questions.value = listOf(
            Question(
                id = 1,
                question = "",
                answers = listOf(
                    Answer(
                        id = 1,
                        text = "",
                        correct = false
                    )
                )
            )
        )
    }
}