package soft.exe.colabora.study.core.entity

import io.ktor.network.sockets.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
import soft.exe.colabora.study.core.entity.messages.TickTime
import soft.exe.colabora.study.core.entity.messages.TimeOut
import soft.exe.colabora.study.core.service.UserDataService

class ClientPlayer(connection: Socket) : Player(connection) {

    private var currentIndexQuestion: Int = -1

    var numOfQuestions: Int = 0
    var totalTimeInSeconds: Int = 0

    private val _currentQuestion: MutableStateFlow<Question?> = MutableStateFlow(null)
    var currentQuestion: StateFlow<Question?> = _currentQuestion

    private val questionAnswers: MutableList<QuestionAnswer> = mutableListOf()

    private val _results = MutableStateFlow<ExamResults?>(null)
    val results: StateFlow<ExamResults?> = _results

    private val _start = MutableStateFlow(false)
    val start: StateFlow<Boolean> = _start

    private val _finish = MutableStateFlow(false)
    val finish: StateFlow<Boolean> = _finish

    private val _time = MutableStateFlow(0)
    val time: StateFlow<Int> = _time

    private val _timeRemaining = MutableStateFlow("00:00:00")
    val timeRemaining: StateFlow<String> = _timeRemaining

    private suspend fun startTimer() {
        while (this._start.value && !this._finish.value) {
            delay(1000)
            this._time.value++
            val l = this.totalTimeInSeconds - this._time.value
            _timeRemaining.value = "${l/3600}:${(l%3600)/60}:${(l%3600)%60}"
        }
    }

    override suspend fun messageHandler(message: Message?) {
        when(message) {
            is RegistrySuccess -> {
                KoinPlatform.getKoin().get<UserDataService>().send(this::send)
            }
            is StartGameMessage -> {
                this.numOfQuestions = message.numOfQuestions
                this.totalTimeInSeconds = message.totalTimeInSeconds
                this._start.value = true
                CoroutineScope(Dispatchers.Unconfined).launch {
                    startTimer()
                }
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
            is TickTime -> {
                if (this._time.value != message.time)
                    this._time.value = message.time
            }
            is TimeOut -> {
                this._finish.value = true
                this.send(FinishExam(this.questionAnswers, this.time.value))
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
            this.send(FinishExam(this.questionAnswers, this.time.value  ))
            return
        }
        this.send(RequestQuestion(this.currentIndexQuestion))
    }

    fun addQuestionAnswer(questionAnswer: QuestionAnswer) {
        this.questionAnswers.add(questionAnswer)
    }

}