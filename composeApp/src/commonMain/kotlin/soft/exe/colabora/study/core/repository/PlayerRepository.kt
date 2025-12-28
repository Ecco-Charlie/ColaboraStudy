package soft.exe.colabora.study.core.repository

import io.ktor.network.sockets.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.entity.messages.RegistrySuccess

class PlayerRepository {

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players

    private val scope = CoroutineScope(Dispatchers.Unconfined)

    suspend fun register(connection: Socket) {
        val player = Player(connection)
        this._players.update {
            it + player
        }
        scope.launch {
            player.listening(::remove)
        }
        player.send(RegistrySuccess())
    }

    fun remove(player: Player) {
        player.close()
        this._players.update {
            it - player
        }
    }

}