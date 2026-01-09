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
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.exam
import colaborastudy.composeapp.generated.resources.next
import colaborastudy.composeapp.generated.resources.time_remaining
import com.mikepenz.markdown.m3.Markdown
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.ExamController
import soft.exe.colabora.study.ui.components.TitleText
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun ExamScreen(
    controller: ExamController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {

    val question by controller.currentQuestion.collectAsStateWithLifecycle()
    val answerSelected by controller.selectedAnswer.collectAsStateWithLifecycle()
    val time by controller.time.collectAsStateWithLifecycle()
    val timeRemaining by controller.timeRemaining.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        controller.navEvent.collect { onNavigate(it) }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(vertical = 20.dp, horizontal = 40.dp)
        ) {
            TitleText(
                text = stringResource(Res.string.exam),
                size = 30.sp
            )
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { time },
                modifier = Modifier.fillMaxWidth().height(10.dp),
                strokeCap = StrokeCap.Square
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = stringResource(Res.string.time_remaining) + timeRemaining
            )
            Spacer(Modifier.height(10.dp))
            if (question == null) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "Question: ${controller.currentIndexQuestion.value+1}/${controller.numOfQuestions}",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(30.dp))
                Markdown(
                    question!!.question,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(15.dp))
                LazyColumn {
                    items(question?.answers ?: listOf()) {
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
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = controller::nextQuestion,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(Res.string.next)
                )
            }
        }
    }
}