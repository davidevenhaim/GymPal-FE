package com.example.gympal2

import com.example.gympal2.auth.authModule
import com.example.gympal2.core.network.websocketModule
import com.example.gympal2.feature.gym.gymModule
import org.koin.dsl.module

val appModule = module {

    includes(
        authModule,
        gymModule,
        websocketModule
    )

}

