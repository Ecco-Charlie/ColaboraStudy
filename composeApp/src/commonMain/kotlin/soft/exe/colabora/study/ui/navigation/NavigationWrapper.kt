package soft.exe.colabora.study.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import soft.exe.colabora.study.ui.screens.ExamScreen
import soft.exe.colabora.study.ui.screens.HomeScreen
import soft.exe.colabora.study.ui.screens.LobbyScreen
import soft.exe.colabora.study.ui.screens.LoginScreen
import soft.exe.colabora.study.ui.screens.ResultsScreen
import soft.exe.colabora.study.ui.screens.ResultsServerScreen
import soft.exe.colabora.study.ui.screens.WaitScreen

@Composable
fun NavigationWrapper() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen { navigateHandle(navController, it) }
        }
        composable<Login> {
            LoginScreen { navigateHandle(navController, it) }
        }
        composable<Lobby> {
            LobbyScreen { navigateHandle(navController, it) }
        }
        composable<Exam> {
            ExamScreen { navigateHandle(navController, it) }
        }
        composable<Results> {
            ResultsScreen { navigateHandle(navController, it) }
        }
        composable<ResultsServer> {
            ResultsServerScreen { navigateHandle(navController, it) }
        }
        composable<Wait> { backStack ->
            val text: Wait = backStack.toRoute()
            WaitScreen(text = text.text) { navigateHandle(navController, it) }
        }
    }

}

private fun navigateHandle(navController: NavHostController, navEvent: NavigationEvent) {
    when (navEvent) {
        is NavigationEvent.NavigateTo -> navController.navigate(navEvent.route)
        is NavigationEvent.NavigateBack -> navController.popBackStack()
        is NavigationEvent.NavigateToAndClear -> navController.navigate(navEvent.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
        }
    }
}