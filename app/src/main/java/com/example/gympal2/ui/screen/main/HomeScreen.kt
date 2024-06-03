package com.example.gympal2.ui.screen.main

import GymViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gympal2.ui.screen.main.Map.Map
import com.example.gympal2.ui.shared.TopNavBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val gymViewModel: GymViewModel = koinViewModel()
    val gyms by gymViewModel.gyms.collectAsState()

    println("Gyms are: $gyms")

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
//            LocationPermission()
        }
    }
}