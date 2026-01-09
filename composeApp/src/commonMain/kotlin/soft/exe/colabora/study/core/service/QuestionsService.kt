package soft.exe.colabora.study.core.service

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    fun setPromptParameters(pp: PromptParameters) {
        this.promptParameters = pp
    }

    val numOfQuestions: Int get() = this.promptParameters?.numOfQuestions ?: 0
    val totalTimeInSeconds: Int get() = this.promptParameters?.totalTime ?: 0

    private val questionsRepository: QuestionsRepository

    private val _generationError: Channel<String> = Channel()
    val generationError: Flow<String> = _generationError.receiveAsFlow()


    init {
        val geminiApiKey = BuildKonfig.GEMINI_API_KEY
        this.questionsRepository = if (geminiApiKey != null)
            GeminiQuestionsRepository(geminiApiKey)
        else
            LocalQuestionsRepository()
    }

    suspend fun loadQuestions() {
        require(this.promptParameters != null)
        this._questions.value = questionsRepository.getQuestions(this.promptParameters!!)
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