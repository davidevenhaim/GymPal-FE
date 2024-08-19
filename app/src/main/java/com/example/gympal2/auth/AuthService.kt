package com.example.gympal2.auth


import com.example.gympal2.util.LOGIN_USER_URL
import com.example.gympal2.util.REGISTER_USER_URL
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(LOGIN_USER_URL)
    suspend fun login(@Body request: LoginData): UserResponse

    @POST(REGISTER_USER_URL)
    suspend fun register(@Body request: RegisterData): UserResponse
}
