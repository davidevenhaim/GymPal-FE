package com.example.gympal2.model

import java.util.Date

data class Gym(
    val createdAt: Date,
    val id: String,
    val location: Location,
    val name: String,
    val rating: Int,
    val workingHours: List<WorkHours>,
)


data class WorkHours(
    val isOpen: Boolean,
    val start: String,
    val end: String,
    val day: DaysOfWeek,

    )