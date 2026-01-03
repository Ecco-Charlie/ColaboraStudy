package soft.exe.colabora.study.core.entity

import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readInt
import io.ktor.utils.io.readPacket
import kotlinx.io.IOException
import kotlinx.io.readByteArray
import soft.exe.colabora.study.core.entity.messages.Message

abstract class Player(
    private val connection: Socket
    ) : MessageDecoder() {

    private val reader = connection.openReadChannel()
    override val writer = connection.openWriteChannel(autoFlush = true)

    suspend fun listening(onClose: ((Player) -> Unit)? = null) {
        while(!this.reader.isClosedForRead) {
            val size = try { this.reader.readInt() } catch(_: IOException) { break }
            if (size == -1)
                break
            val buffer = this.reader.readPacket(size)
            val msg = this.decodeMessage(buffer.readByteArray())
            this.messageHandler(msg)
        }
        onClose?.invoke(this)
    }

    protected abstract suspend fun messageHandler(message: Message?)

    fun close() {
        this.connection.close()
    }

}