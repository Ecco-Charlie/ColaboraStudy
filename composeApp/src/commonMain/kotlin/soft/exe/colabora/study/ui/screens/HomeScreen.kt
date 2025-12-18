package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.HomeController
import soft.exe.colabora.study.getPlatform
import soft.exe.colabora.study.ui.components.Logo
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun HomeScreen(
    controller: HomeController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {
    val load by controller.load.collectAsState()

    LaunchedEffect(true) {
        controller.navEvent.collect { onNavigate(it) }
    }

    Surface (
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Logo()
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
            Spacer(Modifier.height(10.dp))

            if (load.isLoad) {
                CircularProgressIndicator()
            }

        }
    }
}