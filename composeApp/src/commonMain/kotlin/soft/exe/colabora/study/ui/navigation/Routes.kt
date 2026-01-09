package soft.exe.colabora.study.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Login

@Serializable
object Lobby

@Serializable
object Exam

@Serializable
object Results

@Serializable
object ResultsServer

@Serializable
data class Wait(val text: String)

@Serializable
object Questions