package soft.exe.colabora.study.core.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.decodeToImageBitmap
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readInt
import io.ktor.utils.io.readPacket
import kotlinx.io.IOException
import kotlinx.io.readByteArray
import org.koin.mp.KoinPlatform
import soft.exe.colabora.study.core.entity.messages.ErrorMessage
import soft.exe.colabora.study.core.entity.messages.Message
import soft.exe.colabora.study.core.entity.messages.QuestionMessage
import soft.exe.colabora.study.core.entity.messages.RegistrySuccess
import soft.exe.colabora.study.core.entity.messages.RequestQuestion
import soft.exe.colabora.study.core.entity.messages.StartGameMessage
import soft.exe.colabora.study.core.entity.messages.UserDataMessage
import soft.exe.colabora.study.core.service.QuestionsClient
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.service.UserDataService

class Player(
    private val connection: Socket
    ) : MessageDecoder() {
    private val reader = connection.openReadChannel()
    override val writer = connection.openWriteChannel(autoFlush = true)

    private val koin = KoinPlatform.getKoin()

    var userData by mutableStateOf<UserData?>(null)
        private set

    private var questionsClient: QuestionsClient? = null

    private var questionsService: QuestionsService? = null

    var currentQuestion: Question? = null
    var numOfQuestions: Int? = null

    suspend fun listening(onClose: ((Player) -> Unit)? = null) {
        while(!this.reader.isClosedForRead) {
            val size = try { this.reader.readInt() } catch(_: IOException) { break }
            if (size == -1)
                break
            val buffer = this.reader.readPacket(size)
            val msg = this.decodeMessage(buffer.readByteArray())
            this.messageHandler(msg)
        }
        onClose?.invoke(this)
    }

    private suspend fun messageHandler(message: Message?) {
        when(message) {
            is UserDataMessage -> {
                val picture = message.picture.decodeToImageBitmap()
                this.userData = UserData(
                    username = message.username,
                    picture = picture
                )
            }
            is RegistrySuccess -> {
                koin.get<UserDataService>().send(this::send)
            }
            is StartGameMessage -> {
                this.numOfQuestions = message.numOfQuestions
                questionsClient = QuestionsClient(this.numOfQuestions!!)
                this.requestQuestion()
            }
            is RequestQuestion -> {
                if (this.questionsService == null)
                    this.questionsService = koin.get<QuestionsService>()
                val question = this.questionsService!!.getQuestion(message.questionId)
                this.send(QuestionMessage(question))
            }
            is QuestionMessage -> {
                this.currentQuestion = message.question
            }
            else -> {
                this.send(ErrorMessage("UNKNOW_MESSAGE"))
            }
        }
    }

    fun close() {
        this.connection.close()
    }

    private suspend fun requestQuestion() {
        require(this.questionsClient != null)
        val qid = this.questionsClient!!.nextQuestion()
        this.send(RequestQuestion(qid))
    }

}