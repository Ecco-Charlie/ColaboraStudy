package soft.exe.colabora.study.core.utils

import java.net.InetAddress
import java.net.NetworkInterface

actual fun getLocalIpAddress(): String? {
    return try {
        InetAddress.getLocalHost().hostAddress
    } catch(_: Exception) {
        null
    }
}