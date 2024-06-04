package com.example.gympal2.ui.screen.auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorText(text: String) {
    Text(text = text, color = MaterialTheme.colorScheme.error)
}