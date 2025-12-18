package soft.exe.colabora.study.ui.navigation

sealed class NavigationEvent {
    data class NavigateTo(val route: Any): NavigationEvent()
    object NavigateBack: NavigationEvent()
}
