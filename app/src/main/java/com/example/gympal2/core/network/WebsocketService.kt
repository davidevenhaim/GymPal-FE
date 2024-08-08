import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface WebSocketService {
    @Receive
    fun observeMessages(): Flowable<WebSocketMessage>

    @Send
    fun sendMessage(message: WebSocketMessage)
}

data class WebSocketMessage(val message: String)
