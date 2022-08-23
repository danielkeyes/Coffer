package dev.danielkeyes.coffer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.danielkeyes.coffer.compose.MyNavHost
import dev.danielkeyes.coffer.ui.theme.CofferTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val navController = rememberNavController()

            CofferTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyNavHost(rememberNavController())
                }
            }
        }
    }
}

@Composable
fun MainActivityContent() {
    MyNavHost(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CofferTheme {
        MainActivityContent()
    }
}