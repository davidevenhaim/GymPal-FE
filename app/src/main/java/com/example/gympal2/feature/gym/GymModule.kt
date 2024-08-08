package com.example.gympal2.feature.gym

import com.example.gympal2.core.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gymModule = module {

    single { RetrofitClient.gymService }

    single { GymRepository(get()) }

    viewModel { GymViewModel(get()) }

}