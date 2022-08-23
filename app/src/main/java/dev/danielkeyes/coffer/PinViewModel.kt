package dev.danielkeyes.coffer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val pinRepository: IPinRepository
): ViewModel() {

    private val _pin = MutableLiveData<String>("")
    val pin: LiveData<String>
        get() = _pin

    fun delete(){
        _pin.value = _pin.value?.dropLast(1)
        debug()
    }

    fun submit(onSuccess: () -> Unit, onFailure: () -> Unit){
        // gets pin from secure storage
        // compares to current pin
        // returns if equal
        if(_pin.value?.toInt() == pinRepository.retrievePin()) {
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