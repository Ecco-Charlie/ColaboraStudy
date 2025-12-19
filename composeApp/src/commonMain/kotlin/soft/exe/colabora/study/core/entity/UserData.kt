package soft.exe.colabora.study.core.entity

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserData(
    val username: String,
    val picturePath: String,
    @Transient
    var picture: ImageBitmap? = null
)