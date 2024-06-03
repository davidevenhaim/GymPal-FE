package com.example.gympal2.data.network

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("WebSocket opened: $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Received message: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("Received bytes: ${bytes.hex()}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
        println("WebSocket closing: $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        println("WebSocket error: ${t.message}")
    }
}