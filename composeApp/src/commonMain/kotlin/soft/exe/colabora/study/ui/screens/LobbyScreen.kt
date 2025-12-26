package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.everything_ready
import colaborastudy.composeapp.generated.resources.loading_questions
import colaborastudy.composeapp.generated.resources.no_one_online
import colaborastudy.composeapp.generated.resources.people_connected
import colaborastudy.composeapp.generated.resources.start_game
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.LobbyController
import soft.exe.colabora.study.ui.components.TitleText

@Composable
fun LobbyScreen(controller: LobbyController = koinViewModel()) {

    val load by controller.load.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(vertical = 20.dp, horizontal = 40.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (load.isLoad) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
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
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(Modifier.height(20.dp))
            TitleText(
                text = stringResource(Res.string.people_connected),
                size = 20.sp
            )
            Spacer(Modifier.height(10.dp))
            LazyHorizontalGrid(
                rows = GridCells.Adaptive(minSize = 148.dp),
                modifier = Modifier.weight(1f).fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.medium
                    ).padding(20.dp)
            ) {
                item {
                    Text(
                        text = stringResource(Res.string.no_one_online)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(Res.string.start_game)
                )
            }
        }
    }
}