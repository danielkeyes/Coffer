package dev.danielkeyes.coffer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.compose.ROUTE
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val pinRepository: IPinRepository,
    private val pinUseCase: IPasswordUseCase,
): ViewModel() {
    fun navigate(navController: NavHostController){
        viewModelScope.launch {
            pinRepository.pin.map { pin ->
                if (pinUseCase.isPinValid(pin)) {
                    navController.navigate(ROUTE.SETUP.toString())
                } else {
                    navController.navigate(ROUTE.PINPAGE.toString())
                }
            }

        }
    }
}