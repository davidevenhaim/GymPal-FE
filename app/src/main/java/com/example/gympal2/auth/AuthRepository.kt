package com.example.gympal2.auth

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow


class AuthRepository(
    private val authService: AuthService, // network connection
    private val tokenManager: TokenManager // local connection
) {
    var authState = MutableStateFlow<AuthState>(AuthState.Idle)
        private set

    var _userData = mutableStateOf<UserResponse?>(null)
        private set

    init {
        val token = tokenManager.getToken()
        val userId = tokenManager.getUserId()

        if (tokenManager.isTokenValid(token) && !userId.isNullOrBlank()) {
            authState.value = AuthState.Success(token!!)
        }
    }

    fun getUserId(): String {
        return tokenManager.getUserId() ?: ""
    }

    suspend fun login(username: String, password: String) {
        authState.value = AuthState.Loading
        try {
            val response = authService.login(LoginData(username, password))
            if (response.token.isNotEmpty()) {
                _userData.value = response
                tokenManager.saveToken(response.token)
                tokenManager.saveUserId(response.user.id)
                authState.value = AuthState.Success(response.token)
            } else {
                authState.value = AuthState.Error("Login Failed")
            }

        } catch (e: Exception) {
            authState.value = AuthState.Error("User/Password is incorrect")
        }

    }

    suspend fun register(username: String, name: String, password: String) {
        authState.value = AuthState.Loading
        try {
            val response = authService.register(RegisterData(username, name, password))
            if (response.token.isNotEmpty()) {
                authState.value = AuthState.Success(response.token)
                tokenManager.saveToken(response.token)
                tokenManager.saveUserId(response.user.id)
                _userData.value = response
            } else {
                authState.value = AuthState.Error("Login Failed")
            }
        } catch (e: Exception) {
            authState.value = AuthState.Error("${e.message}")
        }
    }

    fun logout(): Boolean {
        authState.value = AuthState.Idle
        _userData.value = null
        tokenManager.clearToken()
        tokenManager.clearUserId()
        return true
    }
}

