package dev.danielkeyes.coffer.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.danielkeyes.coffer.compose.ROUTE

@Composable
fun Setup( navController: NavHostController) {
    val step = remember{ mutableStateOf(1)}
    val pin1 = remember { mutableStateOf("")}
    val pin2 = remember { mutableStateOf("")}

    val context = LocalContext.current

    if (step.value == 1) Column() {
        Text(text = "Please enter a Pin")
        PinDisplay(pin = getPinDisplay(pin1.value))
        Keypad(onEntry = { pin1.value = pin1.value + it },
            delete = { pin1.value = pin1.value.dropLast(1) },
            submit = {
                if (pin1.value.length > 3) {
                    step.value = 2
                } else {
                    Toast.makeText(
                        context, "Please enter 4 charater of longer pin", Toast.LENGTH_SHORT
                    ).show()
                }
            })
    } else if (step.value == 2) {
        Column() {
            Text(text = "Verify pin")
            PinDisplay(pin = getPinDisplay(pin2.value))
            Keypad(onEntry = { pin2.value = pin2.value + it },
                delete = { pin2.value = pin2.value.dropLast(1) },
                submit = {
                    if (pin2.value == pin1.value) {
                        navController.navigate(ROUTE.PINPAGE.toString())
                    } else {
                        Toast.makeText(
                            context, "Pins did not match", Toast.LENGTH_SHORT
                        ).show()
                        pin1.value = ""
                        pin2.value = ""
                        step.value = 1
                    }
                }
            )
        }

    }
}

@Preview
@Composable
fun PreviewSetup() {
    Setup(navController = rememberNavController())
}

private fun getPinDisplay(pin: String): String {
    return if (pin.isNotEmpty()) {
        "*".repeat(pin.length - 1) + pin.last()
    } else {
        ""
    }
}