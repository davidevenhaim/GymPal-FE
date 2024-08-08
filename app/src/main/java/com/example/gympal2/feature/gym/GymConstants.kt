package com.example.gympal2.feature.gym


import com.example.gympal2.util.Location
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

enum class DaysOfWeek {
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
}

const val GYM_POLLING_DELAY = 20000