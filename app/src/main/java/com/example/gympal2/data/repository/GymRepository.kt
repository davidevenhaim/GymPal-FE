package com.example.gympal2.data.repository

import com.example.gympal2.data.network.GymService
import com.example.gympal2.model.Gym

class GymRepository(private val apiService: GymService) {
    suspend fun getGyms(): List<Gym> {
        return apiService.getAllGyms()
    }
}