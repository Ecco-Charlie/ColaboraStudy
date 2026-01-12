package soft.exe.colabora.study.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int,
    val question: String,
    val answers: List<Answer>
)