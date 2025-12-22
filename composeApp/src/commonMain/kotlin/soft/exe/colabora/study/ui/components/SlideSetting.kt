package soft.exe.colabora.study.ui.components

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SlideSetting(
    name: String,
    value: Float,
    valueToShow: String? = null,
    onValueChange: (Float) -> Unit,
    @IntRange steps: Int,
    range: ClosedFloatingPointRange<Float>
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = valueToShow ?: "${value.toInt()}",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
    Slider(
        value = value,
        onValueChange = onValueChange,
        steps = steps,
        valueRange = range,
    )
}