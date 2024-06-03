package com.example.gympal2.model

import java.util.Date

data class User(
    val createdAt: Date,
    val id: String,
    val name: String,
    val username: String,
    val workouts: Array<Workout>,
)