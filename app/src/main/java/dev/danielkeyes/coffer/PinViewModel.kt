package dev.danielkeyes.coffer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val passwordRepository: IPasswordRepository,
    private val passwordUseCase: IPasswordUseCase
): ViewModel() {

    val salt = passwordRepository.salt.asLiveData()
    val hash = passwordRepository.hash.asLiveData()

    private val _pin = MutableLiveData<String>("")
    val pin: LiveData<String>
        get() = _pin


    fun delete(){
        _pin.value = _pin.value?.dropLast(1)
    }

    fun updatePin(pin: String) {
        viewModelScope.launch {
//            pinRepository.updatePin(pin)
        }
    }

    fun submit(onSuccess: () -> Unit, onFailure: () -> Unit){
        viewModelScope.launch() {
            val hash = hash.value ?: ""
            val salt = salt.value ?: ""

            if(
                _pin.value != null &&
                passwordUseCase.isExpectedPassword(_pin.value!!, salt = salt, expectedHash = hash)
            ){
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    fun entry(char: Char){
        _pin.value = _pin.value.plus(char)
    }
}