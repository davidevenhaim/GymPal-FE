package com.example.gympal2.feature.workout

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val workoutModule = module {
    factory { get<Retrofit>().create(WorkoutService::class.java) }
    
    factory { WorkoutRepository(get(), get(), get()) }

    viewModel { WorkoutViewModel(get(), get()) }
}