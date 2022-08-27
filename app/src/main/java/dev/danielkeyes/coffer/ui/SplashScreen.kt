package dev.danielkeyes.coffer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.danielkeyes.coffer.R
import dev.danielkeyes.coffer.ui.theme.bottomBlue
import dev.danielkeyes.coffer.ui.theme.middleBrown
import dev.danielkeyes.coffer.ui.theme.topBlue

@Composable
fun SplashScreen(blankScreen: Boolean = true) {
    if (!blankScreen) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(topBlue)
                    .weight(3f)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bottomBlue)
                    .weight(3f)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(180.dp)
                    .width(220.dp)
                    .clip(RoundedCornerShape(25))
                    .background(middleBrown),
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(400.dp),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}