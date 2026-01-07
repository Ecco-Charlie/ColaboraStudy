package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TICK_TIME")
data class TickTime(val time: Int) : Message()
