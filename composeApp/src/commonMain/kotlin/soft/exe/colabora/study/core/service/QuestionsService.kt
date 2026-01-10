package soft.exe.colabora.study.core.service

import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.not_support_file
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readString
import io.ktor.util.decodeBase64String
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import soft.exe.colabora.study.BuildKonfig
import soft.exe.colabora.study.core.entity.PromptParameters
import soft.exe.colabora.study.core.entity.Question
import soft.exe.colabora.study.core.entity.QuestionAnswer
import soft.exe.colabora.study.core.entity.QuestionResult
import soft.exe.colabora.study.core.entity.messages.ExamResults
import soft.exe.colabora.study.core.repository.GeminiQuestionsRepository
import soft.exe.colabora.study.core.repository.LocalQuestionsRepository
import soft.exe.colabora.study.core.repository.QuestionsRepository

class QuestionsService {

    private val _questions = MutableStateFlow<List<Question>>(listOf())
    val questions: StateFlow<List<Question>> = _questions

    fun getQuestion(index: Int): Question {
        return this._questions.value[index]
    }

    private var promptParameters: PromptParameters? = null

    private var totalTime: Int = 0

    fun setPromptParameters(pp: PromptParameters) {
        this.promptParameters = pp
    }

    val numOfQuestions: Int get() = this._questions.value.size
    val totalTimeInSeconds: Int get() = this.promptParameters?.totalTime ?: totalTime

    private val questionsRepository: QuestionsRepository = if (BuildKonfig.TEST_MODE)
        LocalQuestionsRepository()
    else
        GeminiQuestionsRepository(BuildKonfig.GEMINI_API_KEY ?: "SOME_GEMINI_API_KEY")

    private val _generationError: Channel<String> = Channel()
    val generationError: Flow<String> = _generationError.receiveAsFlow()

    fun setTime(time: Int) {
        this.totalTime = time
    }

    suspend fun loadQuestions() {
        require(this.promptParameters != null)
        try {
            this._questions.value = questionsRepository.getQuestions(this.promptParameters!!)
        } catch(e: Exception) {
            _generationError.send(e.message.toString())
        }
    }


    suspend fun loadQuestionsFile(file: PlatformFile) {
        try {
            val content = file.readString().decodeBase64String()
            val questionsFromFile: List<Question> = Json.decodeFromString<List<Question>>(content)
            this._questions.value = questionsFromFile
        } catch(e: Exception) {
            _generationError.send(getString(Res.string.not_support_file))
        }
    }

    suspend fun evaluateExam(questionAnswers: List<QuestionAnswer>, onFinished: suspend (ExamResults) -> Unit) {
        var score = 0
        val res = questionAnswers.mapNotNull { question ->
            
            val rQuestion = this._questions.value.find { q -> q.id == question.questionId }
            if (rQuestion == null)
                return@mapNotNull null

            val rAnswer = rQuestion.answers.find { it.id == question.answerId }
            if (rAnswer == null)
                return@mapNotNull null

            val correct = rAnswer.correct
            if (correct) {
                score++
            }

            QuestionResult(
                questionText = rQuestion.question,
                correct = correct,
                selectedAnswer = rAnswer,
                correctAnswer = if (correct) null else rQuestion.answers.find { it.correct }
            )
        }

        onFinished(ExamResults(
            results =  res,
            score = score,
            totalNumOfQuestions = this._questions.value.size
        ))
    }

}