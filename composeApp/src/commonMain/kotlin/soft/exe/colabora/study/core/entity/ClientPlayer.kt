package soft.exe.colabora.study.core.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.decodeToImageBitmap
import io.ktor.network.sockets.Socket
import org.koin.core.Koin
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

class ClientPlayer(connection: Socket) : Player(connection) {

    private var userData by mutableStateOf<UserData?>(null)

    private lateinit var questionsClient: QuestionsClient

    private val koin: Koin = KoinPlatform.getKoin()


    override suspend fun messageHandler(message: Message?) {
        when(message) {
            is RegistrySuccess -> {
                koin.get<UserDataService>().send(this::send)
            }
            is StartGameMessage -> {
                this.numOfQuestions = message.numOfQuestions
                questionsClient = QuestionsClient(this.numOfQuestions!!)
                this.requestQuestion()
            }
            is QuestionMessage -> {
                this.currentQuestion = message.question
            }
            else -> {
                this.send(ErrorMessage("UNKNOW_MESSAGE"))
            }
        }
    }

    private suspend fun requestQuestion() {
        require(this.questionsClient != null)
        val qid = this.questionsClient!!.nextQuestion()
        this.send(RequestQuestion(qid))
    }

}