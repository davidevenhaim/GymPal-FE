package com.example.gympal2.model

enum class DaysOfWeek {
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
}

data class Location(
    val lat: Double,
    val lng: Double
)
