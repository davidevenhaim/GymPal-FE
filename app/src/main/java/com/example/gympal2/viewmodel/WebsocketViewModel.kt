package com.example.gympal2.viewmodel


import WebSocketMessage
import WebSocketService
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gympal2.util.API_WS_URL
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient

class WebSocketViewModel(private val webSocketService: WebSocketService) : ViewModel() {

    private val _messages = MutableLiveData<WebSocketMessage>()
    val messages: LiveData<WebSocketMessage> get() = _messages

    init {
        observeWebSocket()
    }

    @SuppressLint("CheckResult")
    private fun observeWebSocket() {
        webSocketService.observeMessages()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message ->
                _messages.postValue(message)
            }
    }

    fun sendMessage(message: String) {
        println("im sending a message to the websocket")
        webSocketService.sendMessage(WebSocketMessage(message))
    }
}

fun createScarletInstance(): Scarlet {
    val okHttpClient = OkHttpClient.Builder()
        .build()

    return Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(API_WS_URL))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .build()
}