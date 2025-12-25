package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.everything_ready
import colaborastudy.composeapp.generated.resources.loading_questions
import colaborastudy.composeapp.generated.resources.people_connected
import colaborastudy.composeapp.generated.resources.start_game
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.LobbyController
import soft.exe.colabora.study.ui.components.TitleText
import soft.exe.colabora.study.ui.components.TopicContent
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun LobbyScreen(
    controller: LobbyController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {

    val load by controller.load.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 20.dp, horizontal = 40.dp)
        ) {

            if (load.isLoad) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = stringResource(Res.string.loading_questions)
                    )
                }
            } else {
                Text(
                    text = stringResource(Res.string.everything_ready),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 20.dp))

            TitleText(
                text = stringResource(Res.string.people_connected)
            )
            Spacer(Modifier.height(10.dp))
            LazyHorizontalGrid(
                rows = GridCells.Adaptive(minSize = 140.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier.weight(1f).fillMaxWidth().border(
                    BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                    ),
                    shape = MaterialTheme.shapes.medium
                )
            ) {
                items(count = 1) {
                    Text("Aqui no hay nadie")
                }
            }
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {},
                enabled = !load.isLoad,
            ) {
                Text(
                    text = stringResource(Res.string.start_game),
                    fontFamily = MaterialTheme.typography.titleSmall.fontFamily
                )
            }

        }
    }
}