package com.example.gympal2.util

import com.example.gympal2.model.LoginData
import com.example.gympal2.model.RegisterData
import com.example.gympal2.model.WorkoutFormState

data class ValidationResult(val isValid: Boolean, val errorMessage: String? = null)

fun validateString(str: String): ValidationResult {
    return if (str.isBlank()) {
        ValidationResult(false, "Name cannot be empty")
    } else {
        ValidationResult(true)
    }
}

// @@@@@ Auth Validation


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

fun validateLoginForm(formState: LoginData): Map<String, ValidationResult> {
    val errors = mutableMapOf<String, ValidationResult>()
    errors["username"] = validateUsername(formState.username)
    errors["password"] = validatePassword(formState.password)
    return errors
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