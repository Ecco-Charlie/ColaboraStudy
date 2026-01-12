package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("START_GAME")
data class StartGameMessage(
    val numOfQuestions: Int,
    val totalTimeInSeconds: Int
) : Message()