package soft.exe.colabora.study.core.entity

import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.core.toByteArray
import io.ktor.utils.io.readAvailable
import io.ktor.utils.io.writeFully
import kotlinx.serialization.json.Json
import soft.exe.colabora.study.core.entity.messages.ErrorMessage
import soft.exe.colabora.study.core.entity.messages.Message

class Player(connection: Socket) {

    private val reader = connection.openReadChannel()
    private val writer = connection.openWriteChannel(autoFlush = true)
    private var userData: UserData? = null

    suspend fun listening(onClose: (Player) -> Unit) {
        val buffer = ByteArray(1024)
        while(true) {
            val size = reader.readAvailable(buffer)
            if (size == -1)
                break
            decodeMessage(buffer.copyOf(size))
        }
        onClose(this)
    }

    private suspend fun decodeMessage(bytes: ByteArray) {
        try {
            val message: Message = Json.decodeFromString(bytes.decodeToString())
            println(message)
        } catch (_: Exception) {
            this.send(ErrorMessage("UNKNOW_MESSAGE"))
        }
    }

    suspend fun send(msg: Message) {
        val bytes = Json.encodeToString(msg).toByteArray()
        this.writer.writeFully(bytes)
    }

}