package dev.danielkeyes.coffer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetupViewModel: ViewModel() {
    private val _pin = MutableLiveData<String>("")
    val pin: LiveData<String>
        get() = _pin

}