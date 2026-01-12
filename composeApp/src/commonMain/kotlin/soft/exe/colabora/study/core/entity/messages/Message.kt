package soft.exe.colabora.study.core.entity.messages

import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed class Message {
    fun encode(): ByteArray {
        return Json.encodeToString(this).toByteArray()
    }
}