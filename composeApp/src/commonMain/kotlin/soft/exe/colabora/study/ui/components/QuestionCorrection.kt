package soft.exe.colabora.study.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.m3.Markdown
import soft.exe.colabora.study.core.entity.QuestionResult

@Composable
fun QuestionCorrection(qr: QuestionResult) {

    Column (
        modifier = Modifier.border(
            border = BorderStroke(
                width = 2.dp,
                color = if (qr.correct) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            ),
            shape = MaterialTheme.shapes.medium
        ).padding(10.dp)
    ) {
        Markdown(
            content = qr.questionText
        )
        Spacer(Modifier.height(10.dp))
        AnswerField(
            text = qr.selectedAnswer.text,
            selected = qr.correct
        )
        if (!qr.correct) {
            Spacer(Modifier.height(10.dp))
            AnswerField(
                text = qr.correctAnswer!!.text,
                selected = true
            )
        }
    }
}

@Composable
private fun AnswerField(
    text: String,
    selected: Boolean,
    color: Color = if (selected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
) {
    Row(
        modifier = Modifier.height(56.dp).clip(MaterialTheme.shapes.medium)
            .background(
                color = color
            ).padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Markdown(
            content = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}