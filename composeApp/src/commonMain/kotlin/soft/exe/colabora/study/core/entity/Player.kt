package soft.exe.colabora.study.core.entity

import androidx.compose.ui.graphics.decodeToImageBitmap
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readInt
import io.ktor.utils.io.readPacket
import io.ktor.utils.io.writeFully
import io.ktor.utils.io.writeInt
import kotlinx.io.readByteArray
import soft.exe.colabora.study.core.entity.messages.ErrorMessage
import soft.exe.colabora.study.core.entity.messages.Message
import soft.exe.colabora.study.core.entity.messages.UserDataMessage

class Player(private val connection: Socket) : MessageDecoder() {
    private val reader = connection.openReadChannel()
    private val writer = connection.openWriteChannel(autoFlush = true)

    var userData: UserData? = null
        private set

    suspend fun listening(onClose: (Player) -> Unit) {
        while(!this.reader.isClosedForRead) {
            val size = this.reader.readInt()
            if (size == -1)
                break
            val buffer = this.reader.readPacket(size)
            val msg = this.decodeMessage(buffer.readByteArray())
            this.messageHandler(msg)
        }
        onClose(this)
    }

    private suspend fun messageHandler(message: Message?) {
        when(message) {
            is UserDataMessage -> {
                val picture = message.picture.decodeToImageBitmap()
                this.userData = UserData(
                    username = message.username,
                    picturePath = "",
                    picture = picture
                )
            }
            else -> {
                this.send(ErrorMessage("UNKNOW_MESSAGE").encode())
            }
        }
    }

    suspend fun send(bytes: ByteArray) {
        this.writer.writeInt(bytes.size)
        this.writer.writeFully(bytes)
    }

    fun close() {
        this.connection.close()
    }

}