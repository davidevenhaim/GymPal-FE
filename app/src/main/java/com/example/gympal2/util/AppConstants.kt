package com.example.gympal2.util

import com.example.gympal2.core.ui.form.ValidationResult

const val MAX_STRING_LENGTH = 21
const val MIN_STRING_LENGTH = 2
const val MAX_OPEN_INPUT_LENGTH = 60
const val MIN_PASSWORD_LENGTH = 6

var STAR_COUNT = 5
var MIN_RATING_GOLD_BORDER = 4
var MAP_INITIAL_ZOOM = 15f

const val HOME_SCREEN = "home"
const val AUTH_SCREEN = "auth"

const val AUTH_TOKEN = "auth_token"
const val USER_ID = "user_id"

object SnackbarDuration {
    const val Short = 1500
    const val Long = 2750
}

object AuthFields {
    const val Username = "Username"
    const val Name = "Name"
    const val Password = "Password"
}

data class Location(
    val lat: Double,
    val lng: Double
)

data class FormField(
    val value: String,
    var error: ValidationResult,
    val onValueChange: (String) -> Unit,
)

