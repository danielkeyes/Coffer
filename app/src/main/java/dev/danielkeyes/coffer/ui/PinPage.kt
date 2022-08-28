package dev.danielkeyes.coffer.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PinPage(pin: String?,
            onEntry: (Char) -> Unit,
            delete: () -> Unit,
            submit: () -> Unit,
            loading: Boolean = false,
            ) {

    Log.e("dkeyes", "loading $loading")
    Column() {
        Box(modifier = Modifier.weight(1f))
        PinDisplay(pin = pin)
        Box(modifier = Modifier.weight(1f))
        Keypad(
            onEntry = {char ->  onEntry(char)},
            delete = delete,
            submit = submit,
            enabled = !loading
        )
        Box(modifier = Modifier.weight(1f))
    }
}

@Composable
fun PinDisplay(pin: String?) {
    Column(modifier = Modifier.padding(72.dp)) {
        Text(
            text = "*".repeat(pin.toString().length),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 48.dp, bottom = 2.dp,
                )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colors.onBackground)
        )
    }
}

@Composable
fun Keypad(
    onEntry: (Char) -> Unit,
    delete: () -> Unit,
    submit: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Log.e("dkeyes", "enabled = $enabled")
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        val buttonModifier = Modifier
            .weight(1f)
            .fillMaxHeight()
        val rowHeight = 72.dp

        Row( modifier = Modifier.height(rowHeight)){
            KeypadButton(int = 1, onClick = { onEntry('1') }, enabled = enabled, modifier =
                    buttonModifier)
            KeypadButton(int = 2, onClick = { onEntry('2') }, enabled = enabled, modifier = buttonModifier)
            KeypadButton(int = 3, onClick = { onEntry('3') }, enabled = enabled, modifier = buttonModifier)
        }
        Row( modifier = Modifier.height(rowHeight)){
            KeypadButton(int = 4, onClick = { onEntry('4') }, enabled = enabled, modifier = buttonModifier)
            KeypadButton(int = 5, onClick = { onEntry('5') }, enabled = enabled, modifier = buttonModifier)
            KeypadButton(int = 6, onClick = { onEntry('6') }, enabled = enabled, modifier = buttonModifier)
        }
        Row( modifier = Modifier.height(rowHeight)){
            KeypadButton(int = 7, onClick = { onEntry('7') }, enabled = enabled, modifier = buttonModifier)
            KeypadButton(int = 8, onClick = { onEntry('8')}, enabled = enabled, modifier = buttonModifier)
            KeypadButton(int = 9, onClick = { onEntry('9') }, enabled = enabled, modifier = buttonModifier)
        }
        Row(modifier = Modifier.height(rowHeight)) {
            KeypadButton(
                imageVector = Icons.Outlined.ArrowBack,
                description = "Backspace",
                onClick = { delete() },
                enabled = enabled,
                modifier = buttonModifier)
            KeypadButton(int = 0, onClick = {'0' }, enabled = enabled, modifier = buttonModifier)
            KeypadButton(
                imageVector = Icons.Outlined.Check,
                description = "Submit",
                onClick = { submit() },
                enabled = enabled,
                modifier = buttonModifier
            )
        }
    }
}

@Composable
private fun KeypadButton(
    int: Int, onClick: () -> Unit, enabled: Boolean, modifier: Modifier = Modifier
) {
    KeypadButton(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier) {
        Text(
            text = int.toString(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
        )
    }
}

@Composable
private fun KeypadButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    KeypadButton(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier,
    ) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
private fun KeypadButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {
    var keypadModifier = modifier
    if (enabled) {
        keypadModifier = keypadModifier.clickable { onClick() }
    }

    Box(
        modifier = keypadModifier, contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PinPage(pin = "1234", {}, {}, {})
}

@Preview(showBackground = true)
@Composable
fun PreviewKeypad() {
    Keypad({},{},{},)
}