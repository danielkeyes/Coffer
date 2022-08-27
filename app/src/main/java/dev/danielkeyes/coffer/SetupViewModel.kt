package dev.danielkeyes.coffer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
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
            passwordRepository.storePasswordSalt(salt = salt)
            // hash pin
            val passwordHash = passwordUseCase.hash(password = pin, salt = salt)
            // store hash
            passwordRepository.storeHashedPassword(hash = passwordHash)
        }
    }
}