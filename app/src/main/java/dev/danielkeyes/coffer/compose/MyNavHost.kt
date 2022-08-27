package dev.danielkeyes.coffer.compose

import android.widget.Toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.danielkeyes.coffer.ui.PinPage
import dev.danielkeyes.coffer.PinViewModel
import dev.danielkeyes.coffer.SetupViewModel
import dev.danielkeyes.coffer.SplashScreenViewModel
import dev.danielkeyes.coffer.ui.Content
import dev.danielkeyes.coffer.ui.Setup
import dev.danielkeyes.coffer.ui.SplashScreen

enum class ROUTE {
    SPLASH, SETUP, PIN_PAGE, CONTENT,
}

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = ROUTE.SPLASH.toString()
    ) {
        composable(ROUTE.SPLASH.toString()) {
            val parentEntry = remember { navController.getBackStackEntry(ROUTE.SPLASH.toString()) }
            val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>(parentEntry)

            SplashScreen(blankScreen = true)

            LaunchedEffect(Unit) {
                if (splashScreenViewModel.hasCompletedSetup()) {
                    navController.apply {
                        popBackStack()
                        navController.navigate(ROUTE.PIN_PAGE.toString())
                    }
                } else {
                    navController.apply {
                        popBackStack()
                        navigate(ROUTE.SETUP.toString())
                    }
                }
            }
        }

        composable(ROUTE.SETUP.toString()) {
            val parentEntry = remember { navController.getBackStackEntry(ROUTE.SETUP.toString()) }
            val setupViewModel = hiltViewModel<SetupViewModel>(parentEntry)
            Setup(navController = navController, successfulSetup = { pin ->
                setupViewModel.setPin(pin)
            })
        }

        composable(ROUTE.PIN_PAGE.toString()) {
            val parentEntry = remember { navController.getBackStackEntry(ROUTE.PIN_PAGE.toString()) }
            val pinViewModel = hiltViewModel<PinViewModel>(parentEntry)

            val pin by pinViewModel.pin.observeAsState()

            val context = LocalContext.current

            PinPage(pin = pin,
                onEntry = { char -> pinViewModel.entry(char) },
                delete = { pinViewModel.delete() },
                submit = {
                    pinViewModel.submit(onSuccess = {
                        navController.apply {
                            popBackStack()
                            navController.navigate(ROUTE.CONTENT.toString())
                        }
                    }, onFailure = {
                        Toast.makeText(context, "Incorrect Pin", Toast.LENGTH_LONG).show()
                    })
                })
        }

        composable(ROUTE.CONTENT.toString()) {
            Content()
        }
    }
}

