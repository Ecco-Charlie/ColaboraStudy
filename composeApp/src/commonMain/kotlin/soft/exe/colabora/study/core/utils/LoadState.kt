package soft.exe.colabora.study.core.utils

sealed class LoadState {
    object Ok: LoadState()
    object Load: LoadState()

    val isLoad get() = this == Load

}