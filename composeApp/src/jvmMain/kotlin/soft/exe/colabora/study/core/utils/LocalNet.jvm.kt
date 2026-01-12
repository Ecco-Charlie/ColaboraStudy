package soft.exe.colabora.study.core.utils

import java.net.InetAddress

actual fun getLocalIpAddress(): String? {
    return try {
        InetAddress.getLocalHost().hostAddress
    } catch(_: Exception) {
        null
    }
}