package com.example.gympal2.di


import GymViewModel
import WebSocketService
import com.example.gympal2.data.network.RetrofitClient
import com.example.gympal2.data.repository.AuthRepository
import com.example.gympal2.data.repository.GymRepository
import com.example.gympal2.viewmodel.AuthViewModel
import com.example.gympal2.viewmodel.WebSocketViewModel
import com.example.gympal2.viewmodel.createScarletInstance
import com.tinder.scarlet.Scarlet
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient.authService }

    single { AuthRepository(get()) }

    viewModel { AuthViewModel(get(), androidContext()) }

    single { RetrofitClient.gymService }

    single { GymRepository(get()) }

    viewModel { GymViewModel(get()) }

    single { createScarletInstance() }

    single { get<Scarlet>().create(WebSocketService::class.java) }

    viewModel { WebSocketViewModel(get()) }

}

