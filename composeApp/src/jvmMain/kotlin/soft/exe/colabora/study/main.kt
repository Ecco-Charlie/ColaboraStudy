package soft.exe.colabora.study

import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.multiplatform.webview.web.Cef
import io.github.vinceglb.filekit.FileKit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.max

fun main() {
    FileKit.init(appId = "ColaboraStudy")
    System.setProperty("skiko.renderApi", "OPENGL")
    System.setProperty("compose.interop.blending", "true")
    System.setProperty("jdk.gtk.version", "3")

    Thread {
        runBlocking {
            try {
                println("Iniciando Cef...")
                Cef.init(
                    builder = {
                        installDir = File("jcef-bundle")

                        settings {
                            cachePath = File("cache").absolutePath
                            windowlessRenderingEnabled = false
                        }
                        args("--disable-gpu", "--disable-gpu-compositing", "--disable-software-rasterizer", "--disable-software-rasterizer", "--no-sandbox", "--disable-dev-shm-usage", "--ozone-platform=x11", "--disable-gpu-sandbox", "--disable-setuid-sandbox", "--disable-namespace-sandbox")
                    },
                    initProgress = {
                        println("proceso")
                    }
                )
                println("Cef inicializado con éxito")
            } catch (e: Exception) {
                println("Error inicializando Cef: ${e.message}")
                e.printStackTrace()
            }
        }
    }.start()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ColaboraStudy",
        ) {
            App()
        }
    }
}