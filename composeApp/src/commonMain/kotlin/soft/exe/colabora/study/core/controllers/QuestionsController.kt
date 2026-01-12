package soft.exe.colabora.study.core.controllers

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.questions_saved
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.write
import io.ktor.util.encodeBase64
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import soft.exe.colabora.study.core.entity.Question
import soft.exe.colabora.study.core.service.QuestionsService

class QuestionsController(questionService: QuestionsService) : ViewModel()  {

    val questions: StateFlow<List<Question>> = questionService.questions

    val snackState = SnackbarHostState()

    fun saveQuestions() {
        viewModelScope.launch {
            val file = FileKit.openFileSaver(
                suggestedName = "questions",
                extension = "clst"
            )
            if (file == null)
                return@launch
            val jsonQuestions = Json.encodeToString(questions.value)
            file.write(jsonQuestions.encodeBase64().encodeToByteArray())
            snackState.showSnackbar(getString(Res.string.questions_saved) + file.path)
        }
    }

}