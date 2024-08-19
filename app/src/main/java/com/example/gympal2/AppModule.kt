package com.example.gympal2

import NetworkUtil
import android.app.Application
import androidx.room.Room
import com.example.gympal2.auth.authModule
import com.example.gympal2.core.localDB.AppDatabase
import com.example.gympal2.core.localDB.DB_NAME
import com.example.gympal2.core.network.networkModule
import com.example.gympal2.feature.gym.gymModule
import com.example.gympal2.feature.workout.workoutModule
import org.koin.dsl.module

val appModule = module {
    single { get<Application>() }

    single { NetworkUtil(get()) }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, DB_NAME
        ).build()
    }

    includes(
        networkModule,
        authModule,
        gymModule,
        workoutModule
    )

}

