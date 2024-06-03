package com.example.gympal2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Response
import okhttp3.WebSocket

class WebSocketViewModel(private val webSocket: WebSocket) : ViewModel() {

    private val _webSocketMessage = MutableLiveData<String>()
    val webSocketMessage: LiveData<String> get() = _webSocketMessage

    fun onWebSocketOpen(response: Response) {
        _webSocketMessage.postValue("WebSocket opened: $response")
    }

    fun onWebSocketMessage(message: String) {
        println("IM HERERERE")
        _webSocketMessage.postValue(message)
    }

    fun onWebSocketClosing(code: Int, reason: String) {
        _webSocketMessage.postValue("WebSocket closing: $code / $reason")
        webSocket.close(1000, null)
    }

    fun onWebSocketFailure(t: Throwable, response: Response?) {
        _webSocketMessage.postValue("WebSocket error: ${t.message}")
    }

    override fun onCleared() {
        super.onCleared()
        webSocket.close(1000, null)
    }

    fun sendMessage(message: String) {
        println("sending message through the web socket")
        webSocket.send(message)
    }
}
