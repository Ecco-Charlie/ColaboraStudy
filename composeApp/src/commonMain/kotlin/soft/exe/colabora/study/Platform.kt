package soft.exe.colabora.study

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform