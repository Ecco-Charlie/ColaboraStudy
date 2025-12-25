package soft.exe.colabora.study.core.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.ServerSocket
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import soft.exe.colabora.study.core.repository.PlayerRegistry

class ConnectionService(
    private val playerRegistry: PlayerRegistry
) {

    private val selectorManager = SelectorManager(Dispatchers.IO)
    private var ss: ServerSocket? = null
    private var running: Boolean = false

    suspend fun startServer() {
        this.ss = aSocket(this.selectorManager).tcp().bind("0.0.0.0", 9892)
        this.running = true
        clientsHandler()
    }

    private suspend fun clientsHandler() {
        if (this.ss == null)
            throw NullPointerException("The socket is not started")
        while (this.running) {
            val client = this.ss!!.accept()
            playerRegistry.register(client)
        }
    }

    fun stopServer() {
        if (this.ss == null)
            return
        this.running = false
        this.ss!!.close()
    }

}