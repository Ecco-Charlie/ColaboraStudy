package soft.exe.colabora.study.core.service

import androidx.compose.ui.graphics.decodeToImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.serialization.json.Json
import soft.exe.colabora.study.core.entity.UserData
import soft.exe.colabora.study.core.repository.UserDataRepository

class UserDataService(private val udRepository: UserDataRepository) {

    var instance: UserData? = null
        get() = if (field == null) loadUserData() else field
        private set

    fun loadUserData(): UserData? {
        val udJson = udRepository.loadUserDataJson() ?: return null
        val ud: UserData = Json.Default.decodeFromString(udJson)
        instance = ud
        return ud
    }

    suspend fun loadPicture() {
        if (instance == null)
            throw NullPointerException()
        val picture = PlatformFile(instance!!.picturePath).readBytes().decodeToImageBitmap()
        instance!!.picture = picture
    }

    fun saveUserData(ud: UserData) {
        val udJson = Json.encodeToString(ud)
        udRepository.saveUserDataJson(udJson)
        this.instance = ud
    }

}