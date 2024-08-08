package com.example.gympal2.feature.gym

class GymRepository(private val apiService: GymService) {
    suspend fun getGyms(): List<Gym> {
        return apiService.getAllGyms()
    }
}