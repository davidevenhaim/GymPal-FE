package com.example.gympal2.model

data class Workout(
    val name: String,
    val exercises: Array<Exercise>,
    val gym: String
)

data class Exercise(
    var name: String = "",
    var description: String = ""
)

data class WorkoutFormState(
    var name: String = "",
    var exercises: MutableList<Exercise> = mutableListOf()
)