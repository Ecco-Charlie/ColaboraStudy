package soft.exe.colabora.study.core.controllers

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.an_unexpected_error
import colaborastudy.composeapp.generated.resources.image_too_large
import colaborastudy.composeapp.generated.resources.username_cannot_empty
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.crop
import com.attafitamim.krop.core.crop.imageCropper
import com.attafitamim.krop.filekit.encodeToByteArray
import com.attafitamim.krop.filekit.toImageSrc
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.size
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import soft.exe.colabora.study.core.entity.UserData
import soft.exe.colabora.study.core.service.UserDataService
import soft.exe.colabora.study.core.utils.LoadState
import soft.exe.colabora.study.ui.navigation.Home
import soft.exe.colabora.study.ui.navigation.NavigationEvent

class LoginController(private val udService: UserDataService) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _image = MutableStateFlow<ImageBitmap?>(null)
    val image: StateFlow<ImageBitmap?> = _image

    var imageDefault: Boolean = true
        private set

    val imageCropper = imageCropper()

    private val _load = MutableStateFlow<LoadState>(LoadState.Ok)
    val load: StateFlow<LoadState> = _load

    private val _navEvent: Channel<NavigationEvent> = Channel()
    val navEvent = _navEvent.receiveAsFlow()

    val snackState = SnackbarHostState()

    init {
        viewModelScope.launch {
            val defPicture = Res.readBytes("drawable/unknow_user.jpg")
            _image.value = defPicture.decodeToImageBitmap()
        }
    }

    fun onChangeUsername(value: String) {
        this._username.value = value
    }

    fun pickImage() {
        viewModelScope.launch {
            val selectedImage = FileKit.openFilePicker(type = FileKitType.Image)?: return@launch
            if (selectedImage.size() > 5242880) {
                snackState.showSnackbar(getString(Res.string.image_too_large))
                return@launch
            }
            val selectedImageSrc = selectedImage.toImageSrc()
            when (val res = imageCropper.crop(selectedImageSrc)) {
                CropResult.Cancelled -> { return@launch }
                is CropError -> {
                    snackState.showSnackbar(getString(Res.string.an_unexpected_error))
                    return@launch
                }
                is CropResult.Success -> {
                    imageDefault = false
                    _image.value = null
                    _image.value = res.bitmap
                }
            }
        }
    }

    fun removeImage() {
        imageDefault = true
        viewModelScope.launch {
            val defPicture = Res.readBytes("drawable/unknow_user.jpg")
            _image.value = defPicture.decodeToImageBitmap()
        }
    }

    fun saveUserData() {
        if (_username.value.isEmpty()) {
            viewModelScope.launch {
                snackState.showSnackbar(getString(Res.string.username_cannot_empty))
            }
            return
        }
        _load.value = LoadState.Load
        val pFile = PlatformFile(FileKit.filesDir, "avatar")
        viewModelScope.launch {
            pFile.write(_image.value!!.encodeToByteArray())
            val ud = UserData(
                username = _username.value,
                picturePath = pFile.path,
                picture = _image.value
            )
            udService.saveUserData(ud)
            _navEvent.send(NavigationEvent.NavigateToAndClear(route = Home))
        }
    }

}