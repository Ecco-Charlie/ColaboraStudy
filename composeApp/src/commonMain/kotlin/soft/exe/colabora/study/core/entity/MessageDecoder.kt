package soft.exe.colabora.study.core.entity

import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.writeFully
import io.ktor.utils.io.writeInt
import kotlinx.serialization.json.Json
import soft.exe.colabora.study.core.entity.messages.Message

abstract class MessageDecoder {

    protected abstract val writer: ByteWriteChannel

    protected fun decodeMessage(bytes: ByteArray): Message? {
        return try {
            Json.decodeFromString<Message>(bytes.decodeToString())
        } catch(_: Exception) {
            null
        }
    }

    suspend fun send(message: Message) {
        val bytes = message.encode()
        this.writer.writeInt(bytes.size)
        this.writer.writeFully(bytes)
    }

}