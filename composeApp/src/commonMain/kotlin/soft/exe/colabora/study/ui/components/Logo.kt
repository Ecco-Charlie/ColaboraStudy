package soft.exe.colabora.study.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.logo
import colaborastudy.composeapp.generated.resources.logo_dark
import org.jetbrains.compose.resources.painterResource

@Composable
fun Logo(
    modifier: Modifier = Modifier
) {
    Image(
        painterResource(if (isSystemInDarkTheme()) Res.drawable.logo_dark else Res.drawable.logo),
        contentDescription = "ColaboraStudy Logo",
        modifier = modifier.size(250.dp)
    )
}