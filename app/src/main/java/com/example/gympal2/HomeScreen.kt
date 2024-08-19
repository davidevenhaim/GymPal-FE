package com.example.gympal2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.gympal2.core.ui.navbar.TopNavBar
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.feature.gym.GymViewModel
import com.example.gympal2.feature.map.Map
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, onLogout: () -> Unit) {
    val gymViewModel: GymViewModel = koinViewModel()
    val gyms by gymViewModel.getGyms().collectAsState()

    var selectedGym by remember { mutableStateOf<Gym?>(null) }

    fun onChangeGym(newGym: Gym) {
        selectedGym = newGym
    }

    Scaffold(
        topBar = { TopNavBar(onLogout) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Map(navController, gyms, selectedGym) { gym -> onChangeGym(gym) }
        }
    }
}
