package com.example.gympal2.auth.login

import com.example.gympal2.auth.AuthViewModel
import com.example.gympal2.core.ui.form.ValidationResult
import com.example.gympal2.core.ui.form.validatePassword
import com.example.gympal2.core.ui.form.validateUsername
import com.example.gympal2.util.FormField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface LoginStateHolder {
    data class ScreenState(
        val username: FormField,
        val password: FormField,
        val onLoginClicked: () -> Boolean
    )

    val state: StateFlow<ScreenState>

}

class LoginStateHolderImpl(private val authViewModel: AuthViewModel) : LoginStateHolder {
    private val mutableState: MutableStateFlow<LoginStateHolder.ScreenState> = MutableStateFlow(
        LoginStateHolder.ScreenState(
            username = createUsernameField(),
            password = createPasswordField(),
            onLoginClicked = ::submitForm,
        )
    )

    override val state: StateFlow<LoginStateHolder.ScreenState>
        get() = mutableState

    private fun isLoginFormValid(): Boolean {
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
            if (isLoginFormValid()) {
                authViewModel.login(username.value, password.value)
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
