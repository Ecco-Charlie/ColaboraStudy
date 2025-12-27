package soft.exe.colabora.study.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.unknow_user
import org.jetbrains.compose.resources.imageResource
import soft.exe.colabora.study.core.entity.UserData

@Composable
fun PlayerOnline(playerData: UserData?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            playerData?.picture ?: imageResource(Res.drawable.unknow_user),
            contentDescription = "Player online",
            modifier = Modifier.size(55.dp).clip(CircleShape)
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = playerData?.username ?: "Unknow",
            fontSize = 18.sp
        )
    }
}