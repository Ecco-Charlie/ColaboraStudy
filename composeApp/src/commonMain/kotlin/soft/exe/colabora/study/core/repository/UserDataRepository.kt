package soft.exe.colabora.study.core.repository

import com.russhwolf.settings.Settings

class UserDataRepository(private val settings: Settings) {

    fun loadUserDataJson(): String? = settings.getStringOrNull("user_data")

}