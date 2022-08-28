package dev.danielkeyes.coffer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val passwordRepository: IPasswordRepository,
    private val passwordUseCase: IPasswordUseCase
): ViewModel() {

    private val salt = passwordRepository.salt.asLiveData()
    private val hash = passwordRepository.hash.asLiveData()

    private val _pin = MutableLiveData<String>("")
    val pin: LiveData<String>
        get() = _pin

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun delete(){
        _pin.value = _pin.value?.dropLast(1)
    }

    fun clearPin(){
        _pin.value = ""
    }

    fun submit(onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch() {
            _loading.value = true

            val hash = passwordRepository.hash.first()
            val salt = passwordRepository.salt.first()

            Log.e("dkeyes", "submit() hash: $hash")
            Log.e("dkeyes", "submit() salt1: $salt")

            val currentPin = pin.value
            if (currentPin != null &&
                currentPin.isNotEmpty() &&
                salt != null &&
                hash != null &&
                passwordUseCase.isExpectedPassword(
                    _pin.value!!,
                    salt = salt,
                    expectedHash = hash
                )
            ) {
                onSuccess()
            } else {
                onFailure()
            }
            _loading.value = false
        }
    }

    fun entry(char: Char){
        _pin.value = _pin.value.plus(char)
    }
}