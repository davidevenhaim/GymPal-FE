package com.example.gympal2.auth.register

import com.example.gympal2.auth.AuthViewModel
import com.example.gympal2.core.ui.form.ValidationResult
import com.example.gympal2.core.ui.form.validatePassword
import com.example.gympal2.core.ui.form.validateString
import com.example.gympal2.core.ui.form.validateUsername
import com.example.gympal2.util.FormField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface RegisterStateHolder {
    data class ScreenState(
        val username: FormField,
        val password: FormField,
        val name: FormField,
        val onRegisterClicked: () -> Boolean
    )

    val state: StateFlow<ScreenState>
}

class RegisterStateHolderImpl(private val authViewModel: AuthViewModel) : RegisterStateHolder {
    private val mutableState: MutableStateFlow<RegisterStateHolder.ScreenState> = MutableStateFlow(
        RegisterStateHolder.ScreenState(
            username = createUsernameField(),
            name = createNameField(),
            password = createPasswordField(),
            onRegisterClicked = ::submitForm
        )
    )

    override val state: StateFlow<RegisterStateHolder.ScreenState>
        get() = mutableState

    private fun isFormValid(): Boolean {
        mutableState.update {
            it.copy(
                username = it.username.copy(error = validateUsername(it.username.value)),
                password = it.password.copy(error = validatePassword(it.password.value))
            )
        }
        state.value.run {
            return username.error.isValid && password.error.isValid
        }
    }

    private fun submitForm(): Boolean {
        state.value.run {
            if (isFormValid()) {
                authViewModel.register(
                    username = username.value,
                    name = name.value,
                    password = password.value
                )
                return true
            }
        }
        return false
    }


    private fun createUsernameField() = FormField(
        value = "",
        error = ValidationResult(false),
        onValueChange = { newValue ->
            mutableState.update {
                it.copy(
                    username = it.username.copy(
                        value = newValue,
                        error = validateUsername(newValue)
                    )
                )
            }
        },
    )

    private fun createNameField() = FormField(
        value = "",
        error = ValidationResult(false),
        onValueChange = { newValue ->
            mutableState.update {
                it.copy(
                    name = it.name.copy(
                        value = newValue,
                        error = validateString(newValue)
                    )
                )
            }
        },
    )

    private fun createPasswordField() = FormField(
        value = "",
        error = ValidationResult(false),
        onValueChange = { newValue ->

            mutableState.update {
                it.copy(
                    password = it.password.copy(
                        value = newValue,
                        error = validatePassword(newValue)
                    )
                )
            }
        },
    )


}