package soft.exe.colabora.study.core.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import soft.exe.colabora.study.core.entity.Answer
import soft.exe.colabora.study.core.entity.Question
import soft.exe.colabora.study.core.entity.QuestionAnswer
import soft.exe.colabora.study.core.entity.QuestionResult
import soft.exe.colabora.study.core.entity.messages.ExamResults

class QuestionsService {

    private val _questions = MutableStateFlow<List<Question>>(listOf())
    val questions: StateFlow<List<Question>> = _questions

    fun getQuestion(index: Int): Question {
        return this._questions.value[index]
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

    suspend fun loadQuestions(prompt: String) {
        this._questions.value = listOf(
            Question(
                id = 1,
                question = "Why the *sky* is **blue**?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "Because **yes**",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "Because *reflects* the **ocean**",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "Why **not?**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 2,
                question = "Why she not **love** me?\n > This question requires mind",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "Because **yes**",
                        correct = true
                    ),
                    Answer(
                        id = 1,
                        text = "Your **so** *ugly*",
                        correct = false
                    ),
                    Answer(
                        id = 2,
                        text = "Why **not?**",
                        correct = false
                    ),
                )
            ),
        )
    }
}