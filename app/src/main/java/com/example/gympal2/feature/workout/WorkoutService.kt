package com.example.gympal2.feature.workout

import com.example.gympal2.util.CREATE_WORKOUT_URL
import com.example.gympal2.util.GET_GYM_WORKOUT_URL
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkoutService {
    @POST(CREATE_WORKOUT_URL)
    suspend fun createWorkout(@Body request: WorkoutData): Boolean

    @GET("$GET_GYM_WORKOUT_URL/{id}")
    suspend fun getGymWorkouts(@Path("id") gymId: String): List<Workout>?
}