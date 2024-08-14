package com.example.gympal2.feature.workout

import com.example.gympal2.core.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val workoutModule = module {
    factory { RetrofitClient.workoutService }

    factory { WorkoutRepository(get(), get(), get()) }

    viewModel { WorkoutViewModel(get(), get()) }
}