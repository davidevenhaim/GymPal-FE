package com.example.gympal2.feature.gym

import com.example.gympal2.core.localDB.AppDatabase
import com.example.gympal2.core.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gymModule = module {

    factory { RetrofitClient.gymService }

    single { get<AppDatabase>().gymDao() }

    factory { GymRepository(get(), get(), get()) }

    viewModel { GymViewModel(get(), get()) }

}