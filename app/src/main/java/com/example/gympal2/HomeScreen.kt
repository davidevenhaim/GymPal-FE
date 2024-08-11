package com.example.gympal2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gympal2.core.ui.navbar.TopNavBar
import com.example.gympal2.feature.gym.GymViewModel
import com.example.gympal2.feature.map.Map
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val gymViewModel: GymViewModel = koinViewModel()
    val gyms by gymViewModel.getGyms().collectAsState()

    Scaffold(
        topBar = { TopNavBar(onLogout) }
    ) {
        // Main content of the page
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Map(gyms)
        }
    }
}