package soft.exe.colabora.study.core.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.ServerSocket
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import soft.exe.colabora.study.core.entity.ServerPlayer
import soft.exe.colabora.study.core.entity.messages.ShowResults
import soft.exe.colabora.study.core.entity.messages.StartGameMessage
import soft.exe.colabora.study.core.entity.messages.TickTime
import soft.exe.colabora.study.core.entity.messages.TimeOut
import soft.exe.colabora.study.core.repository.PlayerServerRepository

class ConnectionService(private val playerServerRepository: PlayerServerRepository) {

    private val selectorManager = SelectorManager(Dispatchers.IO)
    private var connection: ServerSocket? = null
    private var running: Boolean = false
    val isRunning get() = this.running

    val players: StateFlow<List<ServerPlayer>> = playerServerRepository.players

    private var timeInSeconds: Int = 0

    private var time: Int = 0

    private val _timeRemaining = MutableStateFlow("00:00:00")
    val timeRemaining: StateFlow<String> = _timeRemaining

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

    suspend fun startGame(numOfQuestions: Int, timeInSeconds: Int) {
        this.timeInSeconds = timeInSeconds
        playerServerRepository.sendToAll(StartGameMessage(numOfQuestions, timeInSeconds))
        startTimer()
    }

    suspend fun startTimer() {
        while(this.running && this.time <= this.timeInSeconds) {
            delay(1000)
            this.time++
            val l = this.timeInSeconds - this.time
            _timeRemaining.value = "${(l/3600).toString().padStart(2,'0')}:${((l%3600)/60).toString().padStart(2, '0')}:${((l%3600)%60).toString().padStart(2, '0')}"
            if ((this.time % 5) == 0)
                this.playerServerRepository.sendToAll(TickTime(this.time))
        }
        this.playerServerRepository.sendToAll(TimeOut)
    }

    suspend fun showResults() {
        this.running = false
        this.playerServerRepository.sendToAll(ShowResults)
    }

    fun finishGame() {
        this.time = 0
        this.connection?.close()
    }

}