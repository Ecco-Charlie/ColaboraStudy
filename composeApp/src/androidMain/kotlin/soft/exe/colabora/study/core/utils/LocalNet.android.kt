package soft.exe.colabora.study.core.utils

import java.net.Inet4Address
import java.net.NetworkInterface

actual fun getLocalIpAddress(): String? {
    return NetworkInterface.getNetworkInterfaces().iterator().asSequence()
        .flatMap { it.inetAddresses.asSequence() }
        .filter { it is Inet4Address && !it.isLoopbackAddress }
        .map { it.hostAddress }
        .firstOrNull()
}