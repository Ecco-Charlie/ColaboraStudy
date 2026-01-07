package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.finish_exam
import colaborastudy.composeapp.generated.resources.people_connected
import colaborastudy.composeapp.generated.resources.results
import colaborastudy.composeapp.generated.resources.score
import colaborastudy.composeapp.generated.resources.time
import colaborastudy.composeapp.generated.resources.time_remaining
import colaborastudy.composeapp.generated.resources.username
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.ResultsServerController
import soft.exe.colabora.study.ui.components.PlayerOnline
import soft.exe.colabora.study.ui.components.TitleText
import soft.exe.colabora.study.ui.components.TopicContent
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun ResultsServerScreen(
    controller: ResultsServerController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {

    val players by controller.players.collectAsStateWithLifecycle()
    val timeRemaining by controller.timeRemaining.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        controller.navEvent.collect {
            onNavigate(it)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().safeContentPadding().padding(vertical = 20.dp, horizontal = 40.dp)
        ) {
            TopicContent(
                title = stringResource(Res.string.people_connected),
                heightSpacer = 10.dp
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(players) {
                        PlayerOnline(
                            it.userData,
                            it.finished
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.time_remaining) + timeRemaining
            )
            Spacer(Modifier.height(20.dp))
            TopicContent(
                title = stringResource(Res.string.results),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    TitleText(
                        text = stringResource(Res.string.username),
                        modifier = Modifier.weight(0.3f)
                    )
                    TitleText(
                        text = stringResource(Res.string.score),
                        modifier = Modifier.weight(0.4f)
                    )
                    TitleText(
                        text = stringResource(Res.string.time),
                        modifier = Modifier.weight(0.3f)
                    )
                }
                HorizontalDivider()
                LazyColumn {
                    items(players.filter { it.finished }) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = it.userData!!.username,
                                modifier = Modifier.weight(0.3f)
                            )
                            Text(
                                text = "${it.results!!.score}/${it.results!!.totalNumOfQuestions}",
                                modifier = Modifier.weight(0.4f)
                            )
                            Text(
                                text = it.time,
                                modifier = Modifier.weight(0.3f)
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = controller::finishExam,
                enabled = (players.indexOfFirst { !it.finished } == -1)
            ) {
                Text(
                    text = stringResource(Res.string.finish_exam)
                )
            }
        }
    }
}