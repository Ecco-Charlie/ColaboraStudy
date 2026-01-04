package soft.exe.colabora.study.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class QuestionResult(
    val questionText: String,
    val correct: Boolean,
    val selectedAnswer: Answer,
    val correctAnswer: Answer?
)
