package com.example.gympal2.core.network

import AuthInterceptor
import android.content.Context
import com.example.gympal2.auth.TokenManager
import com.example.gympal2.util.API_BASE_URL
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideTokenProvider(androidContext()) }

    single { AuthInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}

fun provideTokenProvider(context: Context): () -> String? {
    return {
        val tokenManager = TokenManager(context)

        tokenManager.getToken()
    }
}