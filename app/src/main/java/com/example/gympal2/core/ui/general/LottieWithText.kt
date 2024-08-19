package com.example.gympal2.core.ui.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LottieWithText(animation: Int, modifier: Modifier, text: String, fontSize: TextUnit = 20.sp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LottieAnimationView(animation, modifier)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(220.dp),
            fontSize = fontSize,

            )
    }
}