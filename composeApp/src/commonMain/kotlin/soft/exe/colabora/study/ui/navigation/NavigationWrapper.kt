package soft.exe.colabora.study.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import soft.exe.colabora.study.ui.screens.HomeScreen
import soft.exe.colabora.study.ui.screens.LoginScreen

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
    }

}

private fun navigateHandle(navController: NavHostController, navEvent: NavigationEvent) {
    when (navEvent) {
        is NavigationEvent.NavigateTo -> navController.navigate(navEvent.route)
        is NavigationEvent.NavigateBack -> navController.popBackStack()
        is NavigationEvent.NavigateToAndClear -> navController.navigate(navEvent.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
        }
    }
}