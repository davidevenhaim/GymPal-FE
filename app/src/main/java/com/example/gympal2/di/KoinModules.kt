package com.example.gympal2.di


import GymViewModel
import com.example.gympal2.data.network.MyWebSocketListener
import com.example.gympal2.data.network.RetrofitClient
import com.example.gympal2.data.repository.AuthRepository
import com.example.gympal2.data.repository.GymRepository
import com.example.gympal2.util.API_WS_URL
import com.example.gympal2.viewmodel.AuthViewModel
import com.example.gympal2.viewmodel.WebSocketViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
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

    single { OkHttpClient() }

    single {
        val client: OkHttpClient = get()
        val request = Request.Builder().url(API_WS_URL).build()
        client.newWebSocket(request, MyWebSocketListener())
    }

    viewModel { WebSocketViewModel(get()) }

}


