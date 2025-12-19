package soft.exe.colabora.study.core.controllers

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import colaborastudy.composeapp.generated.resources.Res
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.crop
import com.attafitamim.krop.core.crop.imageCropper
import com.attafitamim.krop.filekit.toImageSrc
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.size
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginController : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _image = MutableStateFlow<ImageBitmap?>(null)
    val image: StateFlow<ImageBitmap?> = _image

    val imageCropper = imageCropper()

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
                return@launch
            }
            val selectedImageSrc = selectedImage.toImageSrc()
            when (val res = imageCropper.crop(selectedImageSrc)) {
                CropResult.Cancelled -> { return@launch }
                is CropError -> { return@launch }
                is CropResult.Success -> {
                    _image.value = null
                    _image.value = res.bitmap
                }
            }
        }
    }

}