package com.example.gympal2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gympal2.data.network.WebSocketClient
import com.example.gympal2.util.API_WS_URL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WebSocketViewModel2 : ViewModel() {

    private val _messageFlow = MutableStateFlow<String?>(null)
    val messageFlow: StateFlow<String?> = _messageFlow

    private val webSocketClient = WebSocketClient(
        url = API_WS_URL,
        onMessageReceived = { message ->
            viewModelScope.launch {
                println("@@@@@@@@@@@@@@@@@@@@@@@@@")
                println("Message received! $message")
                _messageFlow.value = "Hello"
            }
        },
        onConnectionClosed = { reason ->
            viewModelScope.launch {
                _messageFlow.value = "Connection closed: $reason"
            }
        },
        onFailure = { throwable ->
            viewModelScope.launch {
                _messageFlow.value = "Error: ${throwable.message}"
            }
        }
    )

    init {
        webSocketClient.connect()
    }

    fun sendMessage(message: String) {
        webSocketClient.send(message)
    }

    override fun onCleared() {
        super.onCleared()
        webSocketClient.close()
    }
}
