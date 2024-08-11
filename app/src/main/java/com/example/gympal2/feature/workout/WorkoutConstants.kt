package com.example.gympal2.feature.workout

data class Workout(
    val name: String = "",
    val exercises: List<Exercise> = MutableList(1) { Exercise("", "") },
    val gym: String = ""
)

data class Exercise(
    var name: String = "",
    var description: String = ""
)

data class WorkoutFormState(
    var name: String = "",
    var exercises: List<Exercise> = listOf()
)