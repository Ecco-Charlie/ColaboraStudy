package soft.exe.colabora.study.core

import kotlinx.serialization.json.Json
import soft.exe.colabora.study.core.entity.UserData
import soft.exe.colabora.study.core.repository.UserDataRepository

class UserDataService(private val udRepository: UserDataRepository) {

    var instance: UserData? = null
        get() = if (field == null) loadUserData() else field
        private set

    private fun loadUserData(): UserData? {
        val udJson = udRepository.loadUserDataJson() ?: return null
        val ud: UserData = Json.decodeFromString(udJson)
        instance = ud
        return ud
    }


}