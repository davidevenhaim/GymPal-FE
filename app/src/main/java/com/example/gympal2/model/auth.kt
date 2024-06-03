package com.example.gympal2.model

data class LoginData(
    val username: String,
    val password: String
)

data class RegisterData(
    val username: String,
    val name: String,
    val password: String
)

data class UpdateUserData(
    val name: String?,
    val username: String?,
    val workouts: Array<Workout>?,
)

data class UserState(val token: String, val user: User)

data class UserStorageData(val token: String, val id: String)