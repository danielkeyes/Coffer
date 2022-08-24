package dev.danielkeyes.coffer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val pinRepository: IPinRepository
): ViewModel() {

    private val _pin = MutableLiveData<String>("")
    val pin: LiveData<String>
        get() = _pin

    val storedPin = pinRepository.pin.asLiveData()

    fun delete(){
        _pin.value = _pin.value?.dropLast(1)
        debug()
    }

    fun updatePin(pin: String) {
        viewModelScope.launch {
            pinRepository.updatePin(pin)
        }
    }

    fun submit(onSuccess: () -> Unit, onFailure: () -> Unit){
        // gets pin from secure storage
        // compares to current pin
        // returns if equal
        if(storedPin.value != null && storedPin.value == pin.value) {
            onSuccess()
        } else {
            onFailure()
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