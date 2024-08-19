package com.example.gympal2.util

import com.example.gympal2.core.ui.form.ValidationResult

const val MAX_STRING_LENGTH = 21
const val MIN_STRING_LENGTH = 2
const val MAX_OPEN_INPUT_LENGTH = 60
const val MIN_PASSWORD_LENGTH = 6

var STAR_COUNT = 5
var MIN_RATING_GOLD_BORDER = 4
var MAP_INITIAL_ZOOM = 15f


object SnackbarDuration {
    const val Short = 1500
    const val Long = 2750
}

data class Location(
    var lat: Double,
    var lng: Double
)

data class FormField(
    val value: String,
    var error: ValidationResult,
    val onValueChange: (String) -> Unit,
)

