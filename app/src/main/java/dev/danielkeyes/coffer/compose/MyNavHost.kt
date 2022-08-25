package dev.danielkeyes.coffer.compose

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
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
import dev.danielkeyes.coffer.SplashScreenViewModel
import dev.danielkeyes.coffer.ui.Content
import dev.danielkeyes.coffer.ui.Setup
import dev.danielkeyes.coffer.ui.SplashScreen

enum class ROUTE {
    SPLASH,
    SETUP,
    PINPAGE,
    CONTENT,
}

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ROUTE.SPLASH.toString()) {

        composable(ROUTE.SPLASH.toString()){
            val parentEntry = remember { navController.getBackStackEntry(ROUTE.SPLASH.toString()) }
            val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>(parentEntry)

            Log.e("dkeyes", "splash route")

            SplashScreen(
                navController = navController,
                navigate = {
                    splashScreenViewModel.navigate(navController)
                           },
                )
        }

        composable(ROUTE.SETUP.toString()) {
            Log.e("dkeyes", "setup")
            Setup(navController = navController)
            Log.e("dkeyes", "setup2")

        }

        composable(ROUTE.PINPAGE.toString()) {
            val parentEntry = remember { navController.getBackStackEntry(ROUTE.PINPAGE.toString()) }
            val pinViewModel = hiltViewModel<PinViewModel>(parentEntry)

//            val pinViewModel: PinViewModel = viewModel()
            val pin by pinViewModel.pin.observeAsState()

            val context = LocalContext.current

            PinPage(
                pin = pin,
                navController = navController,
                onEntry = {char -> pinViewModel.entry(char)},
                delete = { pinViewModel.delete()},
                submit = { pinViewModel.submit(
                    onSuccess = {
                        navController.navigate(ROUTE.CONTENT.toString())
                    },
                    onFailure = {
                        Toast.makeText(context, "Incorrect Pin", Toast.LENGTH_LONG).show()
                    }
                )},
            )
        }

        composable(ROUTE.CONTENT.toString()){
            Content()
        }
    }
}