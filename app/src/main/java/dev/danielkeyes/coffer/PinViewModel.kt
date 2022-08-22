package dev.danielkeyes.coffer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinViewModel: ViewModel() {

    private val _pin = MutableLiveData<String>("")
    val pin: LiveData<String>
        get() = _pin

    fun delete(){
        _pin.value = _pin.value?.dropLast(1)
        debug()
    }

    fun submit(): Boolean{
        // gets pin from secure storage
        // compares to current pin
        // returns if equal
        return _pin.value == "1111"
    }

    fun entry(char: Char){
        _pin.value = _pin.value.plus(char)
        debug()
    }

    private fun debug(){
        Log.e("dkeyes", "pin: ${pin.value}")
    }

}