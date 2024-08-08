package com.example.gympal2.feature.map.GymBottomSheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.core.ui.TabItem
import com.example.gympal2.core.ui.tabs.AppTabs
import com.example.gympal2.core.ui.general.StarRating

@Composable
fun GymDetailsBottomSheet(gym: Gym) {

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

    StarRating(gym.rating)

    AppTabs(tabItems = tabItems)

}
