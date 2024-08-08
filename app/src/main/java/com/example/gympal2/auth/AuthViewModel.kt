package com.example.gympal2.auth


import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel(), KoinComponent {

    fun getAuthState(): MutableState<AuthState> = authRepository.authState

    fun login(username: String, password: String) {
        viewModelScope.launch { authRepository.login(username, password) }
    }

    fun register(username: String, name: String, password: String) {
        viewModelScope.launch { authRepository.register(username, name, password) }
    }

    fun logout() = authRepository.logout()
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}