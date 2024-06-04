package com.example.gympal2.ui.screen.main.Map.GymBottomSheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gympal2.model.Gym
import com.example.gympal2.model.TabItem
import com.example.gympal2.ui.shared.AppTabs
import com.example.gympal2.ui.shared.StarRating
import com.example.gympal2.viewmodel.WebSocketViewModel
import com.example.gympal2.viewmodel.WebSocketViewModel2
import org.koin.androidx.compose.koinViewModel

@Composable
fun GymDetailsBottomSheet(gym: Gym, viewModel: WebSocketViewModel2 = viewModel()) {
    val message by viewModel.messageFlow.collectAsState()

    val webSocketViewModel: WebSocketViewModel = koinViewModel()
    webSocketViewModel.sendMessage("im sending a message to the websocket")

    val tabItems = listOf(
        TabItem(
            title = "Info",
            screen = { GymInfoTab(gym) }
        ),
        TabItem(
            title = "Training History",
            screen = { TrainingHistoryTab() }
        ),
    )

    Text(
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.titleLarge,
        text = gym.name,
    )

//    Text(text = message ?: "No messages yet", modifier = Modifier.padding(16.dp))
//
//
//    Button(onClick = {
//        viewModel.sendMessage("sending message")
//    }) {
//        Text("Send Message")
//    }

    StarRating(gym.rating)

    AppTabs(tabItems = tabItems)

}
