package soft.exe.colabora.study

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import soft.exe.colabora.study.ui.navigation.NavigationWrapper
import soft.exe.colabora.study.ui.theme.ColaboraStudyTheme

@Composable
fun App() {
    KoinApplication(application = { modules( appModule ) }) {
        ColaboraStudyTheme {
            NavigationWrapper()
        }
    }
}

