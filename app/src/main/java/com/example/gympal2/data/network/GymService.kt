package com.example.gympal2.data.network

import com.example.gympal2.model.Gym
import com.example.gympal2.util.GET_ALL_GYMS_URL
import retrofit2.http.GET

interface GymService {
    @GET(GET_ALL_GYMS_URL)
    suspend fun getAllGyms(): List<Gym>
}