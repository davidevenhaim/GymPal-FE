package com.example.gympal2.auth

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow


class AuthRepository(
    private val authService: AuthService, // network connection
    private val tokenManager: TokenManager // local connection
) {
    var authState = MutableStateFlow<AuthState>(AuthState.Idle)
        private set

    private val _userData = mutableStateOf<UserResponse?>(null)

    init {
        val token = tokenManager.getToken()

        if (tokenManager.isTokenValid(token)) {
            authState.value = AuthState.Success(token!!)
        }
    }

    suspend fun login(username: String, password: String) {
        authState.value = AuthState.Loading
        try {
            val response = authService.login(LoginData(username, password))
            if (response.token.isNotEmpty()) {
                _userData.value = response
                tokenManager.saveToken((response.token))
                authState.value = AuthState.Success(response.token)
            } else {
                println("Setting auth state value to error.")
                authState.value = AuthState.Error("Login Failed")
            }

        } catch (e: Exception) {
            println("Setting auth state value to error.")
            authState.value = AuthState.Error("User/Password is incorrect")
        }

        println("Auth State: ${authState.value}")
    }

    suspend fun register(username: String, name: String, password: String) {
        try {
            val response = authService.register(RegisterData(username, name, password))

            authState.value = AuthState.Success(response.token)
            _userData.value = response
        } catch (e: Exception) {
            authState.value = AuthState.Error("${e.message}")
        }
    }

    fun logout(): Boolean {
        authState.value = AuthState.Idle
        _userData.value = null
        tokenManager.clearToken()
        return true
    }

    suspend fun getUserFromToken(data: UserStorageData): UserResponse {
        return authService.getUserFromToken(data)
    }
}

