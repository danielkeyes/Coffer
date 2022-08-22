package dev.danielkeyes.coffer.compose

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.danielkeyes.coffer.ui.PinPage
import dev.danielkeyes.coffer.PinViewModel
import dev.danielkeyes.coffer.ui.Content

enum class ROUTE {
    PINPAGE,
    CONTENT,
}

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ROUTE.PINPAGE.toString()) {

        composable(ROUTE.PINPAGE.toString()) {
            val pinViewModel = viewModel<PinViewModel>()
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