package soft.exe.colabora.study.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.camera_icon
import colaborastudy.composeapp.generated.resources.create_profile
import colaborastudy.composeapp.generated.resources.next
import colaborastudy.composeapp.generated.resources.tap_to_add_avatar
import colaborastudy.composeapp.generated.resources.trash
import colaborastudy.composeapp.generated.resources.username
import colaborastudy.composeapp.generated.resources.your_username
import com.attafitamim.krop.ui.ImageCropperDialog
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import soft.exe.colabora.study.core.controllers.LoginController
import soft.exe.colabora.study.ui.navigation.NavigationEvent

@Composable
fun LoginScreen(
    controller: LoginController = koinViewModel(),
    onNavigate: (NavigationEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val username by controller.username.collectAsState()
    val image by controller.image.collectAsState()
    val cropState = controller.imageCropper.cropState
    val load by controller.load.collectAsState()

    LaunchedEffect(true) {
        controller.navEvent.collect {
            onNavigate(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(controller.snackState) }
    ) {

        if (cropState != null)
        {
            ImageCropperDialog(
                state = cropState,
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().safeContentPadding().padding(30.dp).verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(Res.string.create_profile),
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                fontSize = 35.sp
            )
            Spacer(Modifier.weight(1f))

            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .clickable(onClick = controller::pickImage)
                ) {
                    if (image == null) {
                        CircularProgressIndicator()
                    } else {
                        Image(
                            bitmap = image!!,
                            contentDescription = "Unknow User"
                        )
                    }
                }
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.size(50.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    onClick = { if (!controller.imageDefault) controller.removeImage() }
                ) {
                    Icon(
                        imageVector = vectorResource( if (controller.imageDefault) Res.drawable.camera_icon else Res.drawable.trash),
                        contentDescription = "Camera Icon",
                        modifier = Modifier.fillMaxSize().padding(8.dp)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(Res.string.tap_to_add_avatar),
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(Modifier.height(40.dp))
            Column(
                modifier = Modifier.widthIn(max = 350.dp).fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.username),
                    fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                    fontSize = 25.sp
                )
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = controller::onChangeUsername,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(stringResource(Res.string.your_username))
                    }
                )
                Spacer(Modifier.height(20.dp))
                if (load.isLoad) {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Button(
                        onClick = controller::saveUserData,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(Res.string.next),
                            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily
                        )
                    }
                }
            }
            Spacer(Modifier.weight(1f))
        }
    }
}