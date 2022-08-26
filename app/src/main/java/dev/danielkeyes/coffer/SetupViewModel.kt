package dev.danielkeyes.coffer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
import dev.danielkeyes.coffer.usecase.PasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val passwordRepository: IPasswordRepository,
    private val passwordUseCase: IPasswordUseCase
): ViewModel() {
    fun setPin(pin: String) {
        viewModelScope.launch {
            // generate salt
            val salt = passwordUseCase.generateSalt()
            //store salt
            passwordRepository.storePasswordSalt(salt)
            // hash pin
            val passwordHash = passwordUseCase.hash(pin, salt)
            // store hash
            passwordRepository.storeHashedPassword(passwordHash)
        }
    }
}