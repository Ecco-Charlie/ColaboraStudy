package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.waiting_results
import org.jetbrains.compose.resources.stringResource

@Composable
fun WaitResultsScreen() {
    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(Res.string.waiting_results)
            )
            Spacer(Modifier.height(20.dp))
            CircularProgressIndicator()
        }
    }
}