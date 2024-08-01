package com.example.gympal2.data.repository


import com.example.gympal2.data.network.AuthService
import com.example.gympal2.model.LoginData
import com.example.gympal2.model.RegisterData
import com.example.gympal2.model.UpdateUserData
import com.example.gympal2.model.UserState
import com.example.gympal2.model.UserStorageData

class AuthRepository(private val authService: AuthService) {
    suspend fun login(username: String, password: String): UserState {
        println("IM on login function")
        return authService.login(LoginData(username, password))
    }

    suspend fun register(username: String, name: String, password: String): UserState {
        return authService.register(RegisterData(username, name, password))
    }

    suspend fun getUserFromToken(data: UserStorageData): UserState {
        return authService.getUserFromToken(data)
    }

    suspend fun updateUser(data: UpdateUserData): UserState {
        return authService.updateUser(data)
    }
}

