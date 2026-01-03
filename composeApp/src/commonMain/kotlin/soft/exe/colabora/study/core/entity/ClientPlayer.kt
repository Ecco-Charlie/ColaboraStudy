package soft.exe.colabora.study.core.entity

import io.ktor.network.sockets.Socket
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.mp.KoinPlatform
import soft.exe.colabora.study.core.entity.messages.ErrorMessage
import soft.exe.colabora.study.core.entity.messages.Message
import soft.exe.colabora.study.core.entity.messages.QuestionMessage
import soft.exe.colabora.study.core.entity.messages.RegistrySuccess
import soft.exe.colabora.study.core.entity.messages.RequestQuestion
import soft.exe.colabora.study.core.entity.messages.StartGameMessage
import soft.exe.colabora.study.core.service.UserDataService

class ClientPlayer(connection: Socket) : Player(connection) {

    private var currentIndexQuestion: Int = -1

    var numOfQuestions: Int = 0
    private val _currentQuestion: MutableStateFlow<Question?> = MutableStateFlow(null)
    var currentQuestion: StateFlow<Question?> = _currentQuestion

    private val _finish: Channel<Boolean> = Channel()
    val finish: Flow<Boolean> = _finish.receiveAsFlow()

    private val questionAnswers: MutableList<QuestionAnswer> = mutableListOf()


    override suspend fun messageHandler(message: Message?) {
        when(message) {
            is RegistrySuccess -> {
                KoinPlatform.getKoin().get<UserDataService>().send(this::send)
            }
            is StartGameMessage -> {
                this.numOfQuestions = message.numOfQuestions
            }
            is QuestionMessage -> {
                this._currentQuestion.value = message.question
            }
            else -> {
                this.send(ErrorMessage("UNKNOW_MESSAGE"))
            }
        }
    }

    suspend fun requestQuestion() {
        this.currentIndexQuestion += 1
        this._currentQuestion.value = null
        if (this.currentIndexQuestion >= this.numOfQuestions) {
            this._finish.send(true)
            return
        }
        this.send(RequestQuestion(this.currentIndexQuestion))
    }

    fun addQuestionAnswer(questionAnswer: QuestionAnswer) {
        this.questionAnswers.add(questionAnswer)
    }

}