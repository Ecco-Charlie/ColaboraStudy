package soft.exe.colabora.study.core.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.ServerSocket
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.StateFlow
import soft.exe.colabora.study.core.entity.ServerPlayer
import soft.exe.colabora.study.core.entity.messages.StartGameMessage
import soft.exe.colabora.study.core.repository.PlayerServerRepository

class ConnectionService(private val playerServerRepository: PlayerServerRepository) {

    private val selectorManager = SelectorManager(Dispatchers.IO)
    private var connection: ServerSocket? = null
    private var running: Boolean = false
    val isRunning get() = this.running

    val players: StateFlow<List<ServerPlayer>> = playerServerRepository.players

    suspend fun startServer() {
        this.connection = aSocket(selectorManager).tcp().bind("0.0.0.0", 9892)
        this.running = true
        connectionHandle()
    }

    private suspend fun connectionHandle() {
        if (this.connection == null || !this.running)
            throw Exception("The Server Socket is not defined or not running")
        try {
            while(this.connection != null && this.running) {
                val client = this.connection!!.accept()
                playerServerRepository.register(client)
            }
        } catch(_: Exception) {
            this.playerServerRepository.finish()
        }
    }

    suspend fun startGame(numOfQuestions: Int) {
        playerServerRepository.sendToAll(StartGameMessage(numOfQuestions))
    }

    fun finishGame() {
        this.running = false
        this.connection?.close()
    }

}