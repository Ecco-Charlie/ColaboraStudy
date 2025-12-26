package soft.exe.colabora.study.core.entity

import kotlinx.serialization.json.Json
import soft.exe.colabora.study.core.entity.messages.Message

abstract class MessageDecoder {

    protected fun decodeMessage(bytes: ByteArray): Message? {
        return try {
            Json.decodeFromString<Message>(bytes.decodeToString())
        } catch(_: Exception) {
            null
        }
    }

}