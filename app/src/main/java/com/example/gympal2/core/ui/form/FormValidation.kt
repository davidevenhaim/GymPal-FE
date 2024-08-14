package com.example.gympal2.core.ui.form

import com.example.gympal2.auth.RegisterData
import com.example.gympal2.feature.workout.WorkoutFormState
import com.example.gympal2.util.MIN_STRING_LENGTH

data class ValidationResult(val isValid: Boolean, val errorMessage: String? = null)

fun validateString(str: String): ValidationResult {
    return if (str.isBlank()) {
        ValidationResult(false, "Field cannot be empty")
    } else {
        ValidationResult(true)
    }
}


fun validateUsername(username: String): ValidationResult {
    return if (username.isBlank()) {
        ValidationResult(false, "Username cannot be empty")
    } else if (username.length < MIN_STRING_LENGTH) {
        ValidationResult(false, "Username must be at least $MIN_STRING_LENGTH length")
    } else {
        ValidationResult(true)
    }
}

fun validatePassword(password: String): ValidationResult {
    return if (password.isBlank()) {
        ValidationResult(false, "Password cannot be empty")
    } else if (password.length < MIN_STRING_LENGTH) {
        ValidationResult(false, "Password must be at least $MIN_STRING_LENGTH length")
    } else {
        ValidationResult(true)
    }
}

fun validateRegisterForm(formState: RegisterData): Map<String, ValidationResult> {
    val errors = mutableMapOf<String, ValidationResult>()
    errors["username"] = validateUsername(formState.username)
    errors["name"] = validateString(formState.name)
    errors["password"] = validatePassword(formState.password)
    return errors
}

fun validateWorkoutForm(formState: WorkoutFormState): Map<String, ValidationResult> {
    val errors = mutableMapOf<String, ValidationResult>()
    errors["name"] = validateString(formState.name)
    formState.exercises.forEachIndexed { index, exercise ->
        errors["exerciseName$index"] = validateString(exercise.name)
        errors["exerciseDescription$index"] = validateString(exercise.description)
    }
    return errors
}