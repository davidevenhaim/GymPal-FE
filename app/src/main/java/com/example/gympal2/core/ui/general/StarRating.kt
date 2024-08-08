package com.example.gympal2.core.ui.general

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.gympal2.util.STAR_COUNT

@Composable
fun StarRating(rating: Int) {

    if (rating in 0..STAR_COUNT) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (i in 0 until rating) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow
                )
            }

            for (i in 0 until STAR_COUNT - rating) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null)
            }
        }
        if (rating == 0) {
            Row {
                Text(
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    text = "Rated $rating",
                )
            }

        }
    }

}