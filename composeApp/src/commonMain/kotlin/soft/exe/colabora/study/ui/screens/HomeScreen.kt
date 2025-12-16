package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.logo
import colaborastudy.composeapp.generated.resources.logo_dark
import org.jetbrains.compose.resources.painterResource
import soft.exe.colabora.study.getPlatform

@Composable
fun HomeScreen() {
    Surface (
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                if (isSystemInDarkTheme()) painterResource(Res.drawable.logo_dark) else painterResource(
                    Res.drawable.logo
                ),
                contentDescription = "ColaboraStudy Logo",
                modifier = Modifier.size(250.dp)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append("Colabora")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        append("Study")
                    }
                },
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                    fontSize = 40.sp
                )
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Running in platform: ${getPlatform().name}"
            )

        }
    }
}