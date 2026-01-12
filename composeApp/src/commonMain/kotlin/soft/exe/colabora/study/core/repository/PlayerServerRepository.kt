package soft.exe.colabora.study.core.repository

import io.ktor.network.sockets.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.entity.ServerPlayer
import soft.exe.colabora.study.core.entity.messages.ExamFinished
import soft.exe.colabora.study.core.entity.messages.Message
import soft.exe.colabora.study.core.entity.messages.RegistrySuccess

class PlayerServerRepository {

    private val _players = MutableStateFlow<List<ServerPlayer>>(emptyList())
    val players: StateFlow<List<ServerPlayer>> = _players

    private val scope = CoroutineScope(Dispatchers.Unconfined)

    suspend fun register(connection: Socket) {
        val player = ServerPlayer(connection)
        this._players.update {
            it + player
        }
        scope.launch {
            player.listening(::remove)
        }
        player.send(RegistrySuccess)
    }

    fun remove(player: Player) {
        player.close()
        player as ServerPlayer
        this._players.update {
            it - player
        }
    }

    suspend fun sendToAll(message: Message) {
        this._players.value.forEach { it.send(message) }
    }

    suspend fun finish() {
        this.sendToAll(ExamFinished)
        this._players.value.forEach { this.remove(it) }
    }

}