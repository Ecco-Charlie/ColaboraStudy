package soft.exe.colabora.study.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.m3.Markdown
import soft.exe.colabora.study.core.entity.Question

@Composable
fun QuestionExam(
    question: Question,
    answerSelected: Int?,
    height: Dp = 15.dp,
    onChangeSelectedAnswer: (Int) -> Unit
) {
    Markdown(
        question.question,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(height))
    question.answers.forEach {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp).selectable(
                selected = (answerSelected == it.id),
                onClick = { onChangeSelectedAnswer(it.id) },
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