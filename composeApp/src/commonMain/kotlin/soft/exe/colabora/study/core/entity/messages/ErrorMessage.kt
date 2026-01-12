package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ERROR")
data class ErrorMessage(
    val cause: String
) : Message()
