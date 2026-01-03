package soft.exe.colabora.study.core.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.ClientPlayer
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.entity.Question

class ConnectionClient(
    private val userDataService: UserDataService
) {

    private val selectorManager = SelectorManager(Dispatchers.Unconfined)
    private val scope = CoroutineScope(Dispatchers.Unconfined)
    private var player: Player? = null

    suspend fun connectToServer(ip: String) {
        val con: Socket = aSocket(selectorManager).tcp().connect(ip, 9892)
        this.player = ClientPlayer(con)
        scope.launch {
            player?.listening {it.close()}
        }
    }

    fun closeConnection() {
        this.player?.close()
    }

    fun nextQuestion(): Question? {
        return player?.currentQuestion
    }

    fun numOfQuestions(): Int {
        return this.player?.numOfQuestions ?: 0
    }

}