package com.example.gympal2.core.network

import com.tinder.scarlet.Scarlet
import WebSocketService
import org.koin.dsl.module

val websocketModule = module {
    single { createScarletInstance() }

    single { get<Scarlet>().create(WebSocketService::class.java) }
}