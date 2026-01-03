package soft.exe.colabora.study.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class QuestionAnswer(
    val questionId: Int,
    val answerId: Int
)
