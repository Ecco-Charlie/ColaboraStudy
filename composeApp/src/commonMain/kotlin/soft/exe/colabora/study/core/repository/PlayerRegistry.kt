package soft.exe.colabora.study.core.repository

import io.ktor.network.sockets.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.Player
import soft.exe.colabora.study.core.entity.messages.RegistrySuccess

class PlayerRegistry {

    private var players: MutableList<Player> = mutableListOf()
    private val scope = CoroutineScope(Dispatchers.Unconfined)


    fun register(client: Socket) {
        val player = Player(client)
        this.players.add(player)
        scope.launch {
            player.listening(::remove)
        }
        scope.launch {
            player.send(RegistrySuccess())
        }
    }

    private fun remove(player: Player) {
        this.players.remove(player)
    }

}