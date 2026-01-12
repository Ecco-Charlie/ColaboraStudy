package soft.exe.colabora.study

import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import soft.exe.colabora.study.core.controllers.ExamController
import soft.exe.colabora.study.core.controllers.HomeController
import soft.exe.colabora.study.core.controllers.LobbyController
import soft.exe.colabora.study.core.controllers.LoginController
import soft.exe.colabora.study.core.controllers.QuestionsController
import soft.exe.colabora.study.core.controllers.ResultsController
import soft.exe.colabora.study.core.controllers.ResultsServerController
import soft.exe.colabora.study.core.controllers.WaitController
import soft.exe.colabora.study.core.repository.PlayerServerRepository
import soft.exe.colabora.study.core.repository.UserDataRepository
import soft.exe.colabora.study.core.service.ConnectionClient
import soft.exe.colabora.study.core.service.ConnectionService
import soft.exe.colabora.study.core.service.QuestionsService
import soft.exe.colabora.study.core.service.UserDataService

val appModule = module {
    viewModelOf(::HomeController)
    viewModelOf(::LoginController)
    single { Settings() }
    singleOf(::UserDataRepository)
    singleOf(::UserDataService)
    singleOf(::QuestionsService)
    viewModelOf(::LobbyController)
    singleOf(::ConnectionService)
    singleOf(::PlayerServerRepository)
    singleOf(::ConnectionClient)
    viewModelOf(::ExamController)
    viewModelOf(::ResultsController)
    viewModelOf(::ResultsServerController)
    viewModelOf(::WaitController)
    viewModelOf(::QuestionsController)
}