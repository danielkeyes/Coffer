package dev.danielkeyes.coffer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(
    navController: NavHostController,
    navigate: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Splash Icon Here")
    }
}
