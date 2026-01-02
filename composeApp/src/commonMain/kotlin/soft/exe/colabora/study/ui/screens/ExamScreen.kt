package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.exam
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.ExamController
import soft.exe.colabora.study.ui.components.TitleText

@Composable
fun ExamScreen(controller: ExamController = koinViewModel()) {

    val load by controller.load.collectAsStateWithLifecycle()
    val question by controller.question.collectAsStateWithLifecycle()
    val state = rememberWebViewState("https://github.com")
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(vertical = 20.dp, horizontal = 40.dp)
        ) {
            WebView(state)
            TitleText(
                text = stringResource(Res.string.exam)
            )
            Text(
                text = "Question: ${question?.id}/${controller.numOfQuestions}",
                color = MaterialTheme.colorScheme.primary
            )
            if (question != null) {
                Text(
                    text = question?.question ?: "UNKNOW"
                )
                LazyColumn {
                    items(question!!.answers) {
                        Row {
                            RadioButton(
                                selected = false,
                                onClick = {}
                            )
                            Text(
                                text = it.text
                            )
                        }
                    }
                }
            }
        }
    }
}