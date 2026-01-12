package soft.exe.colabora.study.core.utils
import kotlinx.cinterop.*
import platform.posix.*
import platform.darwin.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.darwin.ByteVar

@OptIn(ExperimentalForeignApi::class)
actual fun getLocalIpAddress(): String? = memScoped {
    var address: String? = null
    val interfaces = alloc<CPointerVar<ifaddrs>>()
    val scope = this

    if (getifaddrs(interfaces.ptr) == 0) {
        var curr = interfaces.value
        while (curr != null) {
            val addr = curr.pointed.ifa_addr
            if (addr?.pointed?.sa_family?.toInt() == AF_INET) {
                val host = scope.allocArray<ByteVar>(NI_MAXHOST)
                val result = getnameinfo(
                    addr, addr.pointed.sa_len.toUInt(),
                    host.reinterpret(), NI_MAXHOST.toUInt(),
                    null, 0u, NI_NUMERICHOST
                )
                if (result == 0) {
                    val ip = host.toString()
                    if (ip != "127.0.0.1") {
                        address = ip
                        break
                    }
                }
            }
            curr = curr.pointed.ifa_next
        }
        freeifaddrs(interfaces.value)
    }
    address
}