package com.example.gympal2.feature.workout

import com.example.gympal2.core.ui.form.ValidationResult

data class Workout(
    val name: String = "",
    val exercises: MutableList<Exercise> = MutableList(1) { Exercise("", "") },
    val gym: String = ""
)

open class Exercise(
    var name: String = "",
    var description: String = ""
)

data class WorkoutData(
    val name: String = "",
    val exercises: MutableList<Exercise> = MutableList(1) { Exercise("", "") },
    val gym: String = "",
    val user: String = ""
)

data class ExerciseFormField(
    var nameValidation: ValidationResult = ValidationResult(isValid = true),
    var descriptionValidation: ValidationResult = ValidationResult(isValid = true)
) : Exercise("", "")

open class WorkoutFormState(
    var name: String = "",
    var exercises: List<ExerciseFormField> = listOf(),
    var nameValidation: ValidationResult = ValidationResult(isValid = true)
)