package dev.danielkeyes.coffer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
import dev.danielkeyes.coffer.usecase.PasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val passwordRepository: IPasswordRepository,
    private val passwordUseCase: IPasswordUseCase
): ViewModel() {

    val salt = passwordRepository.hash.asLiveData()
    val hash = passwordRepository.salt.asLiveData()

    private val _pin = MutableLiveData<String>("")
    val pin: LiveData<String>
        get() = _pin

//    val storedPin = pinRepository.pin.asLiveData()

    fun delete(){
        _pin.value = _pin.value?.dropLast(1)
        debug()
    }

    fun updatePin(pin: String) {
        viewModelScope.launch {
//            pinRepository.updatePin(pin)
        }
    }

    fun submit(onSuccess: () -> Unit, onFailure: () -> Unit){
        Log.e("dkeyes", "submit called")
        viewModelScope.launch() {
            val hash = hash.value ?: ""
            val salt = salt.value ?: ""

            Log.e("dkeyes", "hash $hash")
            Log.e("dkeyes", "salt $salt")
            Log.e("dkeyes", "pin ${pin.value}")
            Log.e("dkeyes", "password hash ${passwordUseCase.hash(_pin.value!!, salt)}")

            if(
                _pin.value != null &&
                passwordUseCase.hash(_pin.value!!, salt) == hash
            ){
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    fun entry(char: Char){
        _pin.value = _pin.value.plus(char)
        debug()
    }

    private fun debug(){
        Log.e("dkeyes", "pin: ${pin.value}")
    }
}