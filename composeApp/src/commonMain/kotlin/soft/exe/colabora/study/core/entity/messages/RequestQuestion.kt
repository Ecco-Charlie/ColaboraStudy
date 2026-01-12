package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("REQUEST_QUESTION")
data class RequestQuestion(
    val questionId: Int
) : Message()