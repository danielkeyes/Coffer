package dev.danielkeyes.coffer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun Setup( navController: NavHostController) {
  Box(modifier = Modifier.fillMaxSize()){
      Text(text = "Setup Page")
  }
}