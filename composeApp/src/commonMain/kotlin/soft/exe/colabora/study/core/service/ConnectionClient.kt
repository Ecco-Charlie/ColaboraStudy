package soft.exe.colabora.study.core.service

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import soft.exe.colabora.study.core.entity.ClientPlayer
import soft.exe.colabora.study.core.entity.Question
import soft.exe.colabora.study.core.entity.QuestionAnswer
import soft.exe.colabora.study.core.entity.messages.ExamResults

class ConnectionClient {

    private val selectorManager = SelectorManager(Dispatchers.Unconfined)
    private val scope = CoroutineScope(Dispatchers.Unconfined)
    private var player: ClientPlayer? = null

    lateinit var currentQuestion: StateFlow<Question?>

    lateinit var finish: Flow<Boolean>

    lateinit var results: StateFlow<ExamResults?>

    lateinit var start: StateFlow<Boolean>

    suspend fun connectToServer(ip: String) {
        val con: Socket = aSocket(selectorManager).tcp().connect(ip, 9892)
        this.player = ClientPlayer(con)
        scope.launch {
            player?.listening {it.close()}
        }
        this.currentQuestion = player!!.currentQuestion
        this.finish = player!!.finish
        this.results = player!!.results
        this.start = player!!.start
    }

    fun closeConnection() {
        this.player?.close()
    }

    suspend fun nextQuestion() {
        player?.requestQuestion()
    }

    fun numOfQuestions(): Int {
        return this.player?.numOfQuestions ?: 0
    }

    fun registerQuestionAnswer(questionId: Int, answerId: Int) {
        this.player?.addQuestionAnswer(QuestionAnswer(questionId, answerId))
    }

}