package com.example.gympal2.core.network


import com.example.gympal2.auth.AuthService
import com.example.gympal2.feature.gym.GymService
import com.example.gympal2.feature.workout.WorkoutService
import com.example.gympal2.util.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
    val gymService: GymService by lazy {
        retrofit.create(GymService::class.java)
    }

    val workoutService: WorkoutService by lazy {
        retrofit.create(WorkoutService::class.java)
    }

}

