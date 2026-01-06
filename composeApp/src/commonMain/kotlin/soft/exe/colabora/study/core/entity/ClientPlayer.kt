package soft.exe.colabora.study.core.entity

import io.ktor.network.sockets.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.mp.KoinPlatform
import soft.exe.colabora.study.core.entity.messages.ErrorMessage
import soft.exe.colabora.study.core.entity.messages.ExamFinished
import soft.exe.colabora.study.core.entity.messages.ExamResults
import soft.exe.colabora.study.core.entity.messages.FinishExam
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

    private val questionAnswers: MutableList<QuestionAnswer> = mutableListOf()

    private val _results = MutableStateFlow<ExamResults?>(null)
    val results: StateFlow<ExamResults?> = _results

    private val _start = MutableStateFlow(false)
    val start: StateFlow<Boolean> = _start

    private val _finish = MutableStateFlow(false)
    val finish: StateFlow<Boolean> = _finish

    override suspend fun messageHandler(message: Message?) {
        when(message) {
            is RegistrySuccess -> {
                KoinPlatform.getKoin().get<UserDataService>().send(this::send)
            }
            is StartGameMessage -> {
                this.numOfQuestions = message.numOfQuestions
                this._start.value = true
            }
            is QuestionMessage -> {
                this._currentQuestion.value = message.question
            }
            is ExamResults -> {
                this._results.value = message
            }
            is ExamFinished -> {
                this.close()
                this._finish.value = false
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
            this._finish.value = true
            this.send(FinishExam(this.questionAnswers))
            return
        }
        this.send(RequestQuestion(this.currentIndexQuestion))
    }

    fun addQuestionAnswer(questionAnswer: QuestionAnswer) {
        this.questionAnswers.add(questionAnswer)
    }

}