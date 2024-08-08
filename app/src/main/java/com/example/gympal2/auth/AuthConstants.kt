package com.example.gympal2.auth

import com.example.gympal2.feature.workout.Workout
import java.util.Date

data class User(
    val createdAt: Date,
    val id: String,
    val name: String,
    val username: String,
    val workouts: Array<Workout>,
)

data class LoginData(
    var username: String = "",
    var password: String = ""
)

data class RegisterData(
    val username: String = "",
    val name: String = "",
    val password: String = ""
)

data class UpdateUserData(
    val name: String?,
    val username: String?,
    val workouts: Array<Workout>?,
)

data class UserResponse(val token: String, val user: User)

data class UserStorageData(val token: String, val id: String)