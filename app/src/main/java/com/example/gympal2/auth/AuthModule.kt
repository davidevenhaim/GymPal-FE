package com.example.gympal2.auth

import com.example.gympal2.auth.login.LoginStateHolderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    factory { get<Retrofit>().create(AuthService::class.java) }

    factory { TokenManager(androidContext()) }

    single { AuthRepository(get(), get()) }

    factory { LoginStateHolderImpl(get()) }

    viewModel { AuthViewModel(get()) }
}