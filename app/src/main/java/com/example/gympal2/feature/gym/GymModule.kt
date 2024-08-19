package com.example.gympal2.feature.gym

import com.example.gympal2.core.localDB.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val gymModule = module {
    factory { get<Retrofit>().create(GymService::class.java) }
    
    single { get<AppDatabase>().gymDao() }

    factory { GymRepository(get(), get(), get()) }

    viewModel { GymViewModel(get(), get()) }

}