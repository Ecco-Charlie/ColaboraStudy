package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.WaitController
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun WaitScreen(
    text: String,
    controller: WaitController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {

    LaunchedEffect(true) {
        controller.navEvent.collect {
            onNavigate(it)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(Modifier.height(10.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}