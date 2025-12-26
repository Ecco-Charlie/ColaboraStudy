package soft.exe.colabora.study.core.repository

import io.ktor.network.sockets.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.entity.messages.RegistrySuccess

class PlayerRepository {

    private val players: MutableList<Player> = mutableListOf()
    private val scope = CoroutineScope(Dispatchers.Unconfined)

    suspend fun register(connection: Socket) {
        val player = Player(connection)
        this.players.add(player)
        scope.launch {
            player.listening(::remove)
        }
        player.send(RegistrySuccess().encode())
    }

    fun remove(player: Player) {
        player.close()
        this.players.remove(player)
    }

}