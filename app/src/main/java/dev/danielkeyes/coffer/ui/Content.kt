package dev.danielkeyes.coffer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.danielkeyes.coffer.ui.theme.CofferTheme

@Composable
fun Content() {
    val context = LocalContext.current

    // TODO - pull this out to a reusable scaffold
    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Content")
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Icon(imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable {
                    // TODO add menu options to reset pin?
                    Toast.makeText(context, "Menu clicked", Toast.LENGTH_SHORT).show()
                })
        }
    }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Password Accepted")
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewContent() {
    CofferTheme() {
        Content()
    }
}