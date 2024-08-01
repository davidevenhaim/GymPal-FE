package com.example.gympal2.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gympal2.data.repository.AuthRepository
import com.example.gympal2.data.repository.TokenManager
import com.example.gympal2.model.UserState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent


class AuthViewModel(private val authRepository: AuthRepository,
                    private val tokenManager: TokenManager
) :
    ViewModel(), KoinComponent {


    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    private var authState: State<AuthState> = _authState
    private val _userData = mutableStateOf<UserState?>(null)

    fun getAuthState(): State<AuthState> {
        return authState
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.login(username, password)
                if(response.token.isNotEmpty()) {
                    tokenManager.saveToken((response.token))
                    _authState.value = AuthState.Success(response.token)
                }else {
                    _authState.value = AuthState.Error("Login Failed")
                }
            } catch (e: Exception) {
                println("e.localizedMessage: ${e.localizedMessage}")
                _authState.value = AuthState.Error("User/Password is incorrect")
            }
        }
    }

    fun register(username: String, name: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.register(username, name, password)

                _authState.value = AuthState.Success(response.token)
                _userData.value = response
            } catch (e: Exception) {
                println("e.localizedMessage: ${e.localizedMessage}")
                _authState.value = AuthState.Error("${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _authState.value = AuthState.Idle
            _userData.value = null
            tokenManager.clearToken()
        }
    }

}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}