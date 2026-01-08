package soft.exe.colabora.study.core.utils

expect fun getLocalIpAddress(): String?

fun getIpClient(): String? {
    val ip = getLocalIpAddress() ?: return null
    return ip.split(".")[3]
}

private fun getIpRange(): String? {
    val ip = getLocalIpAddress() ?: return null
    val parts = ip.split(".")
    return parts.take(3).joinToString(".") + "."
}

fun getIp(client: Int): String? {
    val range = getIpRange() ?: return null
    return "$range$client"
}