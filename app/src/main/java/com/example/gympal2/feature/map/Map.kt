package com.example.gympal2.feature.map

import NetworkUtil
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.gympal2.NEW_WORKOUT_SCREEN
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.feature.map.GymBottomSheet.GymDetailsBottomSheet
import com.example.gympal2.util.MAP_INITIAL_ZOOM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

val centerTlvLocation = LatLng(32.0853, 34.7818)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Map(
    navController: NavController,
    gyms: List<Gym>,
    selectedGym: Gym?,
    onChangeGym: (Gym) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    val context = LocalContext.current
    val networkUtil = NetworkUtil(context)

    val isOnline by networkUtil.isOnlineState.collectAsState()

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerTlvLocation, MAP_INITIAL_ZOOM)
    }

    LaunchedEffect(key1 = selectedGym) {
        if (selectedGym?.id?.isNotBlank() == true) {
            isSheetOpen = true
        }
    }

    if (isSheetOpen && selectedGym !== null) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize()
        ) {
            GymDetailsBottomSheet(
                selectedGym,
                isOnline = isOnline
            ) {
                navController.navigate("$NEW_WORKOUT_SCREEN/${selectedGym.id}")
            }
        }
    }



    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        for (gym in gyms) {
            GymMarker(
                gym = gym,
                onMarkerClick = {
                    isSheetOpen = true
                    onChangeGym(gym)
                    coroutineScope.launch {
                        changeMapsLocation(cameraPositionState, it)
                    }
                },
                isSelected = selectedGym?.id.equals(gym.id)
            )
        }
    }

}

suspend fun changeMapsLocation(cameraPositionState: CameraPositionState, newPosition: LatLng) {
    val cameraUpdate =
        CameraUpdateFactory.newLatLngZoom(newPosition, cameraPositionState.position.zoom)
    cameraPositionState.animate(cameraUpdate)
}
