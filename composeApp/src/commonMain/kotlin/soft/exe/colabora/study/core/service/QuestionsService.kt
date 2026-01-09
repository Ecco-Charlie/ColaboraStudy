package soft.exe.colabora.study.core.service

import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readString
import io.ktor.util.decodeBase64String
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
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

    var totalTimeInSeconds: Int = 0

    fun setTime(value: Int) {
        this.totalTimeInSeconds = value
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

    suspend fun loadQuestionsFile(file: PlatformFile) {
        val content = file.readString().decodeBase64String()
        val questionsFromFile: List<Question> = Json.decodeFromString<List<Question>>(content)
        this._questions.value = questionsFromFile
    }

    suspend fun loadQuestions(prompt: String) {
        this._questions.value = listOf(
            Question(
                id = 1,
                question = "What is the main **goal** of Charlie Morningstar?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "To *rule* **Hell**",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "To *redeem* **demons** and reduce overpopulation",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "To destroy **Heaven**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 2,
                question = "Who is Charlie's **father**?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "**Lucifer** Morningstar",
                        correct = true
                    ),
                    Answer(
                        id = 1,
                        text = "**Alastor** the Radio Demon",
                        correct = false
                    ),
                    Answer(
                        id = 2,
                        text = "The **Exorcist Angel**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 3,
                question = "What kind of **demon** is Alastor?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "A *Technology* **Demon**",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "A *Radio* **Demon**",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "A *Music* **Demon**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 4,
                question = "Why is **Alastor** helping Charlie?\n > His intentions are *questionable*",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "Because he *truly* believes in **redemption**",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "Because he finds it **entertaining**",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "Because **Lucifer** forced him",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 5,
                question = "What is **Angel Dust** known for?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "Being a *serious* **businessman**",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "Working in the **adult film** industry",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "Leading the **Exorcists**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 6,
                question = "Which **weapon** is commonly used by Exorcist Angels?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "**Holy** weapons",
                        correct = true
                    ),
                    Answer(
                        id = 1,
                        text = "**Demonic** magic",
                        correct = false
                    ),
                    Answer(
                        id = 2,
                        text = "*Radio* **waves**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 7,
                question = "What happens during the **Extermination**?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "Demons are *promoted* to **Heaven**",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "Angels *kill* demons to control **overpopulation**",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "Hell becomes **peaceful**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 8,
                question = "Who is **Vaggie** to Charlie?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "Her *enemy*",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "Her **girlfriend** and protector",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "Her **sister**",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 9,
                question = "What is the **Hazbin Hotel** meant to represent?\n > A *symbolic* place",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "A **trap** for sinners",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "A *chance* for **redemption**",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "A **prison** for demons",
                        correct = false
                    ),
                )
            ),
            Question(
                id = 10,
                question = "Why is **Heaven** concerned about Hell?",
                answers = listOf(
                    Answer(
                        id = 0,
                        text = "Because Hell is *too* **powerful**",
                        correct = false
                    ),
                    Answer(
                        id = 1,
                        text = "Because of **overpopulation** in Hell",
                        correct = true
                    ),
                    Answer(
                        id = 2,
                        text = "Because demons are *invading* **Heaven**",
                        correct = false
                    ),
                )
            ),
        )

    }
}