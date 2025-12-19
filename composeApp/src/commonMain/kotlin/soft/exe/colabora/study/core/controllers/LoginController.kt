package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginController : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    fun onChangeUsername(value: String) {
        this._username.value = value
    }

}