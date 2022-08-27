package dev.danielkeyes.coffer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val passwordRepository: IPasswordRepository,
    private val passwordUseCase: IPasswordUseCase,
) : ViewModel() {

    suspend fun hasCompletedSetup(): Boolean {
        val hash = runBlocking {
            passwordRepository.hash.first()
        }
        return passwordUseCase.isHashValid(hash)
    }
}