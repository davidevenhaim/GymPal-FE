package com.example.gympal2.ui.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.gympal2.util.SnackbarDuration


@Preview(widthDp = 200, heightDp = 700)
@Composable()
fun PreviewSnack() {
    AppSnackbar(message = "Hello")
}

@Composable
fun AppSnackbar(
    message: String,
    actionLabel: String? = null,
    action: (() -> Unit)? = null,
    duration: Int = SnackbarDuration.Short
) {
    val snackbarVisibleState = remember { mutableStateOf(true) }

    if (snackbarVisibleState.value) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = actionLabel?.let {
                {
                    action?.invoke()
                    snackbarVisibleState.value = false
                }
            }

        ) {
            Text(message)
        }
    }
}

