package soft.exe.colabora.study

import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import soft.exe.colabora.study.core.UserDataService
import soft.exe.colabora.study.core.controllers.HomeController
import soft.exe.colabora.study.core.controllers.LoginController
import soft.exe.colabora.study.core.repository.UserDataRepository

val appModule = module {
    viewModelOf(::HomeController)
    viewModelOf(::LoginController)
    single { Settings() }
    singleOf(::UserDataRepository)
    singleOf(::UserDataService)
}