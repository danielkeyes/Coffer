package dev.danielkeyes.coffer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(
    navController: NavHostController,
    checkSetup: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Splash Icon Here")
        checkSetup()
    }
}
