package dev.danielkeyes.coffer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danielkeyes.coffer.compose.ROUTE
import dev.danielkeyes.coffer.usecase.IPasswordUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val passwordRepository: IPasswordRepository,
    private val passwordUseCase: IPasswordUseCase,
): ViewModel() {

    /**
     * Navigates to either setup page or pin page
     */
    fun navigate(navController: NavHostController){
        Log.e("dkeyes", "here")
        viewModelScope.launch {
            delay(2000)
            val hash = passwordRepository.hash.first()

            Log.e("dkeyes", "hash: $hash")

            if (!passwordUseCase.isHashValid(hash)) {
                Log.e("dkeyes", "here2")
                navController.apply {
                    popBackStack()
                    navigate(ROUTE.SETUP.toString())
                }
                Log.e("dkeyes", "here2-1")

            } else {
                Log.e("dkeyes", "here3")

                navController.navigate(ROUTE.PINPAGE.toString())
                Log.e("dkeyes", "here3-1")

            }
        }
    }
}