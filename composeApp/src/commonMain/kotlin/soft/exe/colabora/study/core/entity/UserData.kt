package soft.exe.colabora.study.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val username: String,
    val picturePath: String,
    val picture: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as UserData

        if (username != other.username) return false
        if (picturePath != other.picturePath) return false
        if (!picture.contentEquals(other.picture)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + picturePath.hashCode()
        result = 31 * result + (picture?.contentHashCode() ?: 0)
        return result
    }
}
