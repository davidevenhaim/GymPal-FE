package com.example.gympal2.feature.gym

import com.example.gympal2.util.GET_ALL_GYMS_URL
import retrofit2.http.GET

interface GymService {
    @GET(GET_ALL_GYMS_URL)
    suspend fun getAllGyms(): List<Gym>
}