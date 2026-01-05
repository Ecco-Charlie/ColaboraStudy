package soft.exe.colabora.study.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.check
import colaborastudy.composeapp.generated.resources.unknow_user
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource
import soft.exe.colabora.study.core.entity.UserData

@Composable
fun PlayerOnline(
    playerData: UserData?,
    finished: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(55.dp).clip(CircleShape)) {
            Image(
                playerData?.picture ?: imageResource(Res.drawable.unknow_user),
                contentDescription = "Player online"
            )
            if (finished) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                ) {
                    Icon(
                        vectorResource(Res.drawable.check),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().padding(10.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = playerData?.username ?: "Unknow",
            fontSize = 15.sp
        )
    }
}