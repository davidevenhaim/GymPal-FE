package com.example.gympal2

import NetworkUtil
import android.app.Application
import androidx.room.Room
import com.example.gympal2.auth.authModule
import com.example.gympal2.core.localDB.AppDatabase
import com.example.gympal2.core.localDB.DB_NAME
import com.example.gympal2.core.network.websocketModule
import com.example.gympal2.feature.gym.gymModule
import org.koin.dsl.module

val appModule = module {
    single { get<Application>() }

    // Provide the Room Database

//    single { get<AppDatabase>().offlineRequestDao() }

    factory { NetworkUtil(get()) }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, DB_NAME
        ).build()
    }

    includes(
        authModule,
        gymModule,
        websocketModule
    )

}

