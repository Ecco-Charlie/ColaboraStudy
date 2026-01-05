package soft.exe.colabora.study.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit

@Composable
fun TitleText(
    text: String,
    size: TextUnit = TextUnit.Unspecified,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = size,
        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
        modifier = modifier
    )
}