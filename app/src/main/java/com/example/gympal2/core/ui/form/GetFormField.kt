package com.example.gympal2.core.ui.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gympal2.core.ui.general.AppTextField
import com.example.gympal2.core.ui.general.ErrorText
import com.example.gympal2.util.FormField


@Composable
fun GetFormField(
    field: FormField,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
) {
    field.run {
        AppTextField(
            value,
            onValueChange,
            label,
            modifier = modifier.fillMaxWidth(),
            isError = !error.isValid && !error.errorMessage.isNullOrBlank(),
            isPassword,
        )
        if (!error.isValid && !error.errorMessage.isNullOrBlank()) {
            ErrorText(text = error.errorMessage!!)
        }
    }
}