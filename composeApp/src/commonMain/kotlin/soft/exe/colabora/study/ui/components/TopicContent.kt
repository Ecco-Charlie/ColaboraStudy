package soft.exe.colabora.study.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopicContent(
    title: String,
    titleSize: TextUnit = 25.sp,
    heightSpacer: Dp = 5.dp,
    modifier: Modifier = Modifier.widthIn(max = 450.dp).fillMaxWidth(),
    content: @Composable ColumnScope.() -> Unit
) {
    TitleText(
        text = title,
        size = titleSize
    )
    Spacer(Modifier.height(heightSpacer))
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}