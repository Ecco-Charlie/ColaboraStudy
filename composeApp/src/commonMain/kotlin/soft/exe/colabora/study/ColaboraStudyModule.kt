package soft.exe.colabora.study

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import soft.exe.colabora.study.core.controllers.HomeController

val appModule = module {
    viewModelOf(::HomeController)
}