package com.example.gympal2.auth

import com.example.gympal2.auth.login.LoginStateHolderImpl
import com.example.gympal2.core.network.RetrofitClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory { RetrofitClient.authService }

    factory { TokenManager(androidContext()) }

    factory { AuthRepository(get(), get()) }

    factory { LoginStateHolderImpl(get()) }

    viewModel { AuthViewModel(get()) }
}