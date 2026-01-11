package soft.exe.colabora.study.core.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.decodeToImageBitmap
import io.ktor.network.sockets.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.mp.KoinPlatform
import soft.exe.colabora.study.core.entity.messages.ErrorMessage
import soft.exe.colabora.study.core.entity.messages.ExamResults
import soft.exe.colabora.study.core.entity.messages.FinishExam
import soft.exe.colabora.study.core.entity.messages.Message
import soft.exe.colabora.study.core.entity.messages.QuestionMessage
import soft.exe.colabora.study.core.entity.messages.RequestQuestion
import soft.exe.colabora.study.core.entity.messages.UserDataMessage
import soft.exe.colabora.study.core.service.QuestionsService

class ServerPlayer(connection: Socket) : Player(connection) {

    private var questionsService: QuestionsService = KoinPlatform.getKoin().get<QuestionsService>()

    var userData by mutableStateOf<UserData?>(null)

    var finished by mutableStateOf(false)

    var results by mutableStateOf<ExamResults?>(null)

    var time: String = "00:00:00"

    override suspend fun messageHandler(message: Message?) {
        when(message) {
            is UserDataMessage -> {
                val picture = message.picture.decodeToImageBitmap()
                this.userData = UserData(
                    username = message.username,
                    picture = picture
                )
            }
            is RequestQuestion -> {
                val question = this.questionsService.getQuestion(message.questionId)
                this.send(QuestionMessage(question))
            }
            is FinishExam -> {
                val l = message.time
                this.time = "${(l/3600).toString().padStart(2,'0')}:${((l%3600)/60).toString().padStart(2, '0')}:${((l%3600)%60).toString().padStart(2, '0')}"
                withContext(Dispatchers.Unconfined) {
                    questionsService.evaluateExam(message.questionAnswers) {
                        send(it)
                        results = it
                    }
                }
                this.finished = true
            }
            else -> {
                this.send(ErrorMessage("UNKNOW_MESSAGE"))
            }
        }
    }

}