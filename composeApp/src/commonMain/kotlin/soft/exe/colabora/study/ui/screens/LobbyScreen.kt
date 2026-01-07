package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.connection_address
import colaborastudy.composeapp.generated.resources.everything_ready
import colaborastudy.composeapp.generated.resources.loading_questions
import colaborastudy.composeapp.generated.resources.no_one_online
import colaborastudy.composeapp.generated.resources.participate_exam
import colaborastudy.composeapp.generated.resources.people_connected
import colaborastudy.composeapp.generated.resources.start_game
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.LobbyController
import soft.exe.colabora.study.core.utils.getLocalIpAddress
import soft.exe.colabora.study.ui.components.PlayerOnline
import soft.exe.colabora.study.ui.components.TitleText
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun LobbyScreen(
    controller: LobbyController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {

    val load by controller.load.collectAsStateWithLifecycle()
    val players by controller.players.collectAsStateWithLifecycle()
    val participate by controller.participate.collectAsStateWithLifecycle()
    val ip = remember { getLocalIpAddress() }

    LaunchedEffect(true) {
        controller.navEvent.collect { onNavigate(it) }
    }

    Scaffold (
        snackbarHost = { SnackbarHost(controller.snackState) }
    ) { innerPadding ->
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleText(
                    text = stringResource(Res.string.people_connected),
                    size = 20.sp
                )
                Spacer(Modifier.weight(1f))
                Checkbox(
                    checked = participate,
                    onCheckedChange = { controller.changeParticipation() },
                    modifier = Modifier.height(40.dp)
                )
                Text(
                    text = stringResource(Res.string.participate_exam),
                    modifier = Modifier.clickable(onClick = controller::changeParticipation)
                )
            }
            Spacer(Modifier.height(10.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.FixedSize(100.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.medium
                    ).padding(20.dp),
                verticalItemSpacing = 10.dp
            ) {
                if (players.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(Res.string.no_one_online)
                        )
                    }
                } else {
                    items(players) {
                        PlayerOnline(it.userData)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    append(stringResource(Res.string.connection_address))
                    append(": ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(ip)
                    }
                }
            )
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = controller::startGame,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                enabled = !load.isLoad
            ) {
                Text(
                    text = stringResource(Res.string.start_game)
                )
            }
        }
    }
}