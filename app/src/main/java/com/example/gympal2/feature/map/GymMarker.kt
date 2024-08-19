package com.example.gympal2.feature.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gympal2.R
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.feature.gym.checkIfGymOpen
import com.example.gympal2.util.MIN_RATING_GOLD_BORDER
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.launch

@Composable
fun GymMarker(
    gym: Gym,
    onMarkerClick: (position: LatLng) -> Unit,
    isSelected: Boolean
) {
    val markerPair = getOpenCloseMarkers(gym.rating)
    val coroutineScope = rememberCoroutineScope()

    MarkerComposable(
        state = MarkerState(position = LatLng(gym.location.lat, gym.location.lng)),
        title = gym.name,
        draggable = false,
        onClick = {

            coroutineScope.launch {
                onMarkerClick(it.position)
            }
            return@MarkerComposable true
        }


    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = if (checkIfGymOpen(gym.workingHours)) markerPair.first else markerPair.second),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        3.dp,
                        if (isSelected) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
            )

            Text(text = gym.name, color = Color.Black)

        }

    }
}

fun getOpenCloseMarkers(gymRating: Int): Pair<Int, Int> {
    val openMarker =
        if (gymRating >= MIN_RATING_GOLD_BORDER) R.drawable.gym_marker_open_gold else R.drawable.gym_marker_open
    val closeMarker =
        if (gymRating >= MIN_RATING_GOLD_BORDER) R.drawable.gym_marker_close_gold else R.drawable.gym_marker_close

    return Pair(openMarker, closeMarker)
}