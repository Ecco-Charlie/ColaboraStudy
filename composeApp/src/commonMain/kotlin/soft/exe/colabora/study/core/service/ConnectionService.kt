package soft.exe.colabora.study.core.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.ServerSocket
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.StateFlow
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.entity.messages.StartGameMessage
import soft.exe.colabora.study.core.repository.PlayerRepository

class ConnectionService(private val playerRepository: PlayerRepository) {

    private val selectorManager = SelectorManager(Dispatchers.IO)
    private var connection: ServerSocket? = null
    private var running: Boolean = false

    val players: StateFlow<List<Player>> = playerRepository.players

    suspend fun startServer() {
        this.connection = aSocket(selectorManager).tcp().bind("0.0.0.0", 9892)
        this.running = true
        connectionHandle()
    }

    private suspend fun connectionHandle() {
        if (this.connection == null || !this.running)
            throw Exception("The Server Socket is not defined or not running")
        while(this.connection != null && this.running) {
            val client = this.connection!!.accept()
            playerRepository.register(client)
        }
    }

    suspend fun startGame(numOfQuestions: Int) {
        playerRepository.sendToAll(StartGameMessage(numOfQuestions))
    }

}