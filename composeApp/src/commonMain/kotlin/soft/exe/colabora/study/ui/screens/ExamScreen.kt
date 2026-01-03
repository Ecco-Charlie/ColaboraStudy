package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.exam
import colaborastudy.composeapp.generated.resources.next
import com.mikepenz.markdown.compose.LocalMarkdownColors
import com.mikepenz.markdown.compose.LocalMarkdownTypography
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.compose.elements.MarkdownText
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.ExamController
import soft.exe.colabora.study.ui.components.TitleText

@Composable
fun ExamScreen(controller: ExamController = koinViewModel()) {

    val load by controller.load.collectAsStateWithLifecycle()
    val question by controller.question.collectAsStateWithLifecycle()
    val answerSelected by controller.selectedAnswer.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(vertical = 20.dp, horizontal = 40.dp)
        ) {
            TitleText(
                text = stringResource(Res.string.exam),
                size = 30.sp
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Question: ${question?.id}/${controller.numOfQuestions}",
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(30.dp))
            if (!load.isLoad && question != null) {
                Markdown(
                    question!!.question,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(15.dp))
                LazyColumn {
                    items(question!!.answers) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().height(56.dp).selectable(
                                selected = (answerSelected == it.id),
                                onClick = { controller.changeSelectedAnswer(it.id) },
                                role = Role.RadioButton
                            )
                        ) {
                            RadioButton(
                                selected = (answerSelected == it.id),
                                onClick = null
                            )
                            Spacer(Modifier.width(10.dp))
                            Markdown(
                                it.text,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            } else {
                CircularProgressIndicator()
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { TODO() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(Res.string.next)
                )
            }
        }
    }
}