package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import soft.exe.colabora.study.core.entity.messages.Message

@Serializable
@SerialName("REGISTRY_SUCCESS")
class RegistrySuccess : Message()