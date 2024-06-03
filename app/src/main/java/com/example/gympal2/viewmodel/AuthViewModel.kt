package com.example.gympal2.viewmodel


import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gympal2.data.DataStoreManager
import com.example.gympal2.data.repository.AuthRepository
import com.example.gympal2.model.UserState
import com.example.gympal2.model.UserStorageData
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent


class AuthViewModel(private val authRepository: AuthRepository, private val context: Context) :
    ViewModel(), KoinComponent {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    private var authState: State<AuthState> = _authState
    private val _userData = mutableStateOf<UserState?>(null)
    private var userData: State<UserState?> = _userData

    init {
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        println("Im on Auth View Model INIT FUNCTION")
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
    }

    fun getAuthState(): State<AuthState> {
        return authState
    }

    fun getUser(): State<UserState?> {
        return _userData
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.login(username, password)
                _authState.value = AuthState.Success(response)

                _userData.value = response
                DataStoreManager.saveToken(
                    context,
                    UserStorageData(response.user.id, response.token)
                )
            } catch (e: Exception) {
                println("Error in login: $e.message")
                _authState.value = AuthState.Error(e.message ?: "Unknown Error")

            }
        }
    }

    fun register(username: String, name: String, password: String) {
//        TODO: add real Register logic here and to the authRepository.
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authRepository.register(username, name, password)

                _authState.value = AuthState.Success(response)
                _userData.value = response
                DataStoreManager.saveToken(
                    context,
                    UserStorageData(response.user.id, response.token)
                )
            } catch (e: Exception) {
                println("Error is: $e")
                _authState.value = AuthState.Error(e.message ?: "Login Failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _authState.value = AuthState.Idle
            DataStoreManager.clearUserData(context)
            _userData.value = null
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val authResponse: UserState) : AuthState()
    data class Error(val message: String) : AuthState()
}