package com.example.gympal2.data.network


import com.example.gympal2.model.LoginData
import com.example.gympal2.model.RegisterData
import com.example.gympal2.model.UpdateUserData
import com.example.gympal2.model.UserState

import com.example.gympal2.model.UserStorageData
import com.example.gympal2.util.GET_USER_URL
import com.example.gympal2.util.LOGIN_USER_URL
import com.example.gympal2.util.REGISTER_USER_URL
import com.example.gympal2.util.UPDATE_USER_URL
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST(LOGIN_USER_URL)
    suspend fun login(@Body request: LoginData): UserState

    @POST(REGISTER_USER_URL)
    suspend fun register(@Body request: RegisterData): UserState

    @POST(GET_USER_URL)
    suspend fun getUserFromToken(@Body request: UserStorageData): UserState

    @PATCH(UPDATE_USER_URL)
    suspend fun updateUser(@Body request: UpdateUserData): UserState
}
