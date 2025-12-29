package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import soft.exe.colabora.study.core.entity.Question

@Serializable
@SerialName("QUESTION")
data class QuestionMessage(
    val question: Question
) : Message()