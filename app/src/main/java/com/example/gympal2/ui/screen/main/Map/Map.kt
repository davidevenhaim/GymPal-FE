package com.example.gympal2.ui.screen.main.Map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.gympal2.model.Gym
import com.example.gympal2.util.MAP_ZOOM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

val centerTlvLocation = LatLng(32.0853, 34.7818)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Map(gyms: List<Gym>) {
    val coroutineScope = rememberCoroutineScope()

    var selectedGym by remember { mutableStateOf<Gym?>(null) }
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerTlvLocation, MAP_ZOOM)
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = sheetState
        ) {
            selectedGym?.let { GymDetailsBottomSheet(it) } ?: Box {
                Text(text = "Something went wrong")
            }
        }
    }



    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        for (gym in gyms) {
            GymMarker(
                gym = gym, {
                    isSheetOpen = true
                    selectedGym = gym
                    coroutineScope.launch {
                        changeMapsLocation(cameraPositionState, it)
                    }
                },
                isSelected = selectedGym?.id == gym.id
            )
        }
    }

}

suspend fun changeMapsLocation(cameraPositionState: CameraPositionState, newPosition: LatLng) {
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(newPosition, MAP_ZOOM)
    cameraPositionState.animate(cameraUpdate)
}
