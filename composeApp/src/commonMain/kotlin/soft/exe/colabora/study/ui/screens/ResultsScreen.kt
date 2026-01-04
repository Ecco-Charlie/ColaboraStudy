package soft.exe.colabora.study.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.arrow_down
import colaborastudy.composeapp.generated.resources.arrow_up
import colaborastudy.composeapp.generated.resources.back_top
import colaborastudy.composeapp.generated.resources.show_corrections
import colaborastudy.composeapp.generated.resources.waiting_results
import colaborastudy.composeapp.generated.resources.your_score
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.ResultsController
import soft.exe.colabora.study.ui.components.QuestionCorrection
import soft.exe.colabora.study.ui.navigation.Home
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun ResultsScreen(
    controller: ResultsController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {

    val results by controller.results.collectAsStateWithLifecycle()
    val score by remember { mutableStateOf(results?.score) }
    val numOfQuestions by remember { mutableStateOf(results?.totalNumOfQuestions) }
    var showCorrection by remember { mutableStateOf(false) }

    Scaffold {
        if (results == null) {
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
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(Res.string.your_score)
                )
                Text(
                    text = "$score/$numOfQuestions",
                    fontSize = 35.sp,
                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.selectable(
                        selected = showCorrection,
                        onClick = { showCorrection = !showCorrection },
                        role = Role.Switch
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.show_corrections),
                        fontSize = 15.sp
                    )
                    Icon(
                        vectorResource(if (showCorrection) Res.drawable.arrow_up else Res.drawable.arrow_down),
                        contentDescription = "Show correction"
                    )
                }
                AnimatedVisibility(showCorrection) {
                    Spacer(Modifier.height(10.dp))
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier.widthIn(max = 450.dp)
                    ) {
                        items(results!!.results) {
                            QuestionCorrection(it)
                        }
                    }
                }
                Spacer(Modifier.height(30.dp))
                Button(onClick = {
                    onNavigate(NavigationEvent.NavigateToAndClear(Home))
                    controller.closeConnection()
                }) {
                    Text(
                        text = stringResource(Res.string.back_top)
                    )
                }
            }
        }
    }
}