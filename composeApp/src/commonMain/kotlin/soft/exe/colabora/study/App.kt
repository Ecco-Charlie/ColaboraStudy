package soft.exe.colabora.study

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colaborastudy.composeapp.generated.resources.DynaPuff_VariableFont_wdth
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color(0xffeef1ef))
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(Res.drawable.logo),
                contentDescription = "ColaboraStudy Logo",
                modifier = Modifier.size(250.dp)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff1c2321)
                        )
                    ) {
                        append("Colabora")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff5e6572)
                        )
                    ) {
                        append("Study")
                    }
                },
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(
                            Res.font.DynaPuff_VariableFont_wdth
                        )
                    ),
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