package dev.danielkeyes.coffer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.danielkeyes.coffer.ui.theme.CofferTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pinViewModel: PinViewModel by viewModels<PinViewModel>()
            val pin by pinViewModel.pin.observeAsState()

            CofferTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainActivityContent(
                        pin,
                        { char: Char -> pinViewModel.entry(char) },
                        { pinViewModel.delete() },
                    ) { pinViewModel.submit() }
                }
            }
        }
    }
}

@Composable
fun MainActivityContent(
    pin: String?,
    onEntry: (Char) -> Unit,
    delete: () -> Unit,
    submit: () -> Unit,
) {
    val context = LocalContext.current

//    Column() {
//        PinPage(
//            pin,
//            onEntry = { int ->
//                Toast.makeText(context, int.toString(), Toast.LENGTH_SHORT).show()
//            },
//            delete = { Toast.makeText(context, "delete", Toast.LENGTH_LONG).show() },
//            submit = { Toast.makeText(context, "submit", Toast.LENGTH_LONG).show() })
//    }

    Column() {
        PinPage(
            pin,
            onEntry = onEntry,
            delete = delete,
            submit = submit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CofferTheme {
        MainActivityContent("1234",{},{},{},)
    }
}