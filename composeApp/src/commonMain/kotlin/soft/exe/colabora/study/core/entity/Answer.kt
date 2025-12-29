package soft.exe.colabora.study.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int,
    val text: String,
    val correct: Boolean
)