package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.add_photo
import colaborastudy.composeapp.generated.resources.click_to_photo
import colaborastudy.composeapp.generated.resources.colabora_study
import colaborastudy.composeapp.generated.resources.connection_number
import colaborastudy.composeapp.generated.resources.create_exam
import colaborastudy.composeapp.generated.resources.describe_topic
import colaborastudy.composeapp.generated.resources.difficulties
import colaborastudy.composeapp.generated.resources.difficulty
import colaborastudy.composeapp.generated.resources.exam_settings
import colaborastudy.composeapp.generated.resources.exam_time
import colaborastudy.composeapp.generated.resources.file
import colaborastudy.composeapp.generated.resources.hours
import colaborastudy.composeapp.generated.resources.import_clst_file
import colaborastudy.composeapp.generated.resources.join
import colaborastudy.composeapp.generated.resources.join_exam
import colaborastudy.composeapp.generated.resources.minutes
import colaborastudy.composeapp.generated.resources.number_questions
import colaborastudy.composeapp.generated.resources.required_field
import colaborastudy.composeapp.generated.resources.select_clst_file
import colaborastudy.composeapp.generated.resources.select_file
import colaborastudy.composeapp.generated.resources.start_game
import colaborastudy.composeapp.generated.resources.topic_description
import colaborastudy.composeapp.generated.resources.trash
import colaborastudy.composeapp.generated.resources.upload
import colaborastudy.composeapp.generated.resources.upload_photo
import dev.darkokoa.datetimewheelpicker.WheelTimePicker
import dev.darkokoa.datetimewheelpicker.core.format.TimeFormat
import dev.darkokoa.datetimewheelpicker.core.format.timeFormatter
import io.github.vinceglb.filekit.path
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.HomeController
import soft.exe.colabora.study.core.entity.UserData
import soft.exe.colabora.study.ui.components.Logo
import soft.exe.colabora.study.ui.components.SlideSetting
import soft.exe.colabora.study.ui.components.TitleText
import soft.exe.colabora.study.ui.components.TopicContent
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun HomeScreen(
    controller: HomeController = koinViewModel(),
    reason: String? = null,
    onNavigate: (NavigationEvent) -> Unit
) {
    val load by controller.load.collectAsState()
    val scrollState = rememberScrollState()
    val description by controller.description.collectAsStateWithLifecycle()
    val numOfQuestions by controller.numOfQuestions.collectAsStateWithLifecycle()
    val difficulty by controller.difficulty.collectAsStateWithLifecycle()
    val photo by controller.photo.collectAsStateWithLifecycle()
    val ip by controller.ip.collectAsStateWithLifecycle()
    val selectedClstFile by controller.selectedClstFile.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        controller.navEvent.collect { onNavigate(it) }
    }

    LaunchedEffect(Unit) {
        reason?.let{ controller.snackState.showSnackbar(it, duration = SnackbarDuration.Long) }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(controller.snackState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Head
            Header(userData = controller.userData)

            // Body
            Spacer(Modifier.height(20.dp))
            TopicContent(
                title = stringResource(Res.string.join_exam),
                titleSize = 30.sp
            ) {
                OutlinedTextField(
                    value = ip,
                    onValueChange = controller::onChangeIp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.connection_number)
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = controller::connectToExam,
                    enabled = !load.isLoad,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(Res.string.join),
                        fontFamily = MaterialTheme.typography.titleSmall.fontFamily
                    )
                }
            }

            HorizontalDivider(Modifier.padding(vertical = 20.dp))

            TitleText(
                text = stringResource(Res.string.create_exam),
                size = 30.sp
            )
            Spacer(Modifier.height(15.dp))
            TopicContent(
                title = stringResource(Res.string.topic_description) + " *"
            ) {
                OutlinedTextField(
                    value = description,
                    onValueChange = controller::onDescriptionChange,
                    minLines = 5,
                    maxLines = 5,
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.describe_topic)
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(Res.string.required_field),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 15.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(30.dp))

            TopicContent(
                title = stringResource(Res.string.upload_photo)
            ) {
                UploadImage(
                    photo = photo,
                    onClick = controller::changePhoto,
                    removePhoto = controller::removePhoto
                )
            }

            Spacer(Modifier.height(20.dp))
            HorizontalDivider(Modifier.widthIn(max = 450.dp).fillMaxWidth())
            Spacer(Modifier.height(20.dp))

            TopicContent(
                title = stringResource(Res.string.exam_settings)
            ) {
                SlideSetting(
                    name = stringResource(Res.string.number_questions),
                    value = numOfQuestions,
                    onValueChange = controller::onChangeNumOfQuestions,
                    steps = 4,
                    range = 5f..30f
                )
                Spacer(Modifier.height(15.dp))
                SlideSetting(
                    name = stringResource(Res.string.difficulty),
                    value = difficulty,
                    valueToShow = stringArrayResource(Res.array.difficulties)[difficulty.toInt()],
                    onValueChange = controller::onChangeDifficulty,
                    steps = 1,
                    range = 0f..2f
                )
                Spacer(Modifier.height(15.dp))
                Text(
                    text = stringResource(Res.string.exam_time),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(Res.string.hours))
                    Spacer(Modifier.width(10.dp))
                    WheelTimePicker(
                        timeFormatter = timeFormatter(TimeFormat.HOUR_24),
                        startTime = LocalTime(0,0),
                        size = DpSize(128.dp, 100.dp)
                    ) { time ->
                        controller.onChangeHours(time.hour)
                        controller.onChangeMinutes(time.minute)
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(stringResource(Res.string.minutes))
                }
            }
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = controller::generateQuestions,
                modifier = Modifier.widthIn(max = 450.dp).fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                enabled = !load.isLoad
                ) {
                Text(
                    text = stringResource(Res.string.create_exam),
                    fontFamily = MaterialTheme.typography.titleSmall.fontFamily
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 20.dp))
            Spacer(Modifier.height(20.dp))
            TopicContent(
                title = stringResource(Res.string.import_clst_file),
                titleSize = 25.sp
            ) {
                Button(
                    onClick = controller::selectClstFile,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(Res.string.select_clst_file),
                        fontFamily = MaterialTheme.typography.titleSmall.fontFamily
                    )
                }

                if (selectedClstFile != null) {
                    Text(
                        text = stringResource(Res.string.file) + ": ${selectedClstFile!!.path}"
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(Res.string.hours))
                    Spacer(Modifier.width(10.dp))
                    WheelTimePicker(
                        timeFormatter = timeFormatter(TimeFormat.HOUR_24),
                        startTime = LocalTime(0,0),
                        size = DpSize(128.dp, 100.dp)
                    ) { time ->
                        controller.onChangeHours(time.hour)
                        controller.onChangeMinutes(time.minute)
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(stringResource(Res.string.minutes))
                }

                Button(
                    onClick = controller::startGameWithFile,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(Res.string.start_game),
                        fontFamily = MaterialTheme.typography.titleSmall.fontFamily
                    )
                }
            }

        }
    }

}

@Composable
private fun Header(userData: UserData?) {
    Row(
        modifier = Modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Logo(Modifier.size(50.dp))
        Spacer(Modifier.width(10.dp))
        TitleText(
            text = stringResource(Res.string.colabora_study),
            size = 30.sp
        )
        Spacer(Modifier.weight(1f))
        if (userData != null) {
            Image(
                userData.picture!!,
                contentDescription = "Avatar",
                modifier = Modifier.clip(CircleShape).aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = userData.username
            )
        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun UploadImage(
    accentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onClick: () -> Unit,
    photo: ImageBitmap?,
    removePhoto: () -> Unit
) {
    Box (
        modifier = Modifier
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                ),
                shape = MaterialTheme.shapes.medium
            )
            .widthIn(max = 350.dp)
            .aspectRatio(1.5f)
            .clickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (photo == null) {
                    Icon(
                        vectorResource(Res.drawable.add_photo),
                        contentDescription = "Add photo",
                        modifier = Modifier.size(50.dp),
                        tint = accentColor
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = stringResource(Res.string.click_to_photo),
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            vectorResource(Res.drawable.upload),
                            contentDescription = "Upload photo",
                            modifier = Modifier.size(30.dp),
                            tint = accentColor
                        )
                        Text(
                            text = stringResource(Res.string.select_file),
                            color = accentColor
                        )
                    }
                } else {
                    Image(
                        photo,
                        contentDescription = "Photo reference"
                    )
                }
            }

            if (photo != null) {
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.size(60.dp).padding(10.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    onClick = removePhoto
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.trash),
                        contentDescription = "Trash Icon",
                        modifier = Modifier.fillMaxSize().padding(8.dp)
                    )
                }
            }

        }
    }
}