package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.arrow_back
import colaborastudy.composeapp.generated.resources.exam_questions
import colaborastudy.composeapp.generated.resources.export_questions
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.QuestionsController
import soft.exe.colabora.study.ui.components.QuestionExam
import soft.exe.colabora.study.ui.components.TitleText
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun QuestionsScreen(
    controller: QuestionsController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(controller.snackState) }
    ) {
        Column(
            modifier = Modifier.safeContentPadding().padding(vertical = 20.dp, horizontal = 40.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onNavigate(NavigationEvent.NavigateBack) }) {
                    Icon(
                        vectorResource(Res.drawable.arrow_back),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Spacer(Modifier.weight(1f))
                TitleText(
                    text = stringResource(Res.string.exam_questions),
                    size = 35.sp
                )
                Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.widthIn(max = 450.dp).fillMaxWidth().weight(1f)
            ) {
                items(controller.questions.value) {
                    QuestionExam(it, null, height = 10.dp) {}
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(10.dp))
                }
            }
            Spacer(Modifier.height(10.dp))

            Button(
                onClick = controller::saveQuestions
            ) {
                Text(
                    text = stringResource(Res.string.export_questions)
                )
            }
        }
    }

}