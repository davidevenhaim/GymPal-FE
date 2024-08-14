package com.example.gympal2.feature.map.GymBottomSheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gympal2.core.ui.TabItem
import com.example.gympal2.core.ui.general.StarRating
import com.example.gympal2.core.ui.tabs.AppTabs
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.feature.workout.WorkoutFormStateHolderImpl
import com.example.gympal2.feature.workout.WorkoutViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GymDetailsBottomSheet(gym: Gym) {
    val workoutViewModel: WorkoutViewModel = koinViewModel()
    val workoutFormStateHolder = remember { WorkoutFormStateHolderImpl(workoutViewModel) }
    val workouts by workoutViewModel.getWorkouts().collectAsState()

    LaunchedEffect(key1 = gym) {
        workoutViewModel.getGymWorkouts(gymId = gym.id)
    }

    val tabItems = listOf(
        TabItem(
            title = "Info",
            screen = { GymInfoTab(gym, workoutFormStateHolder.state) }
        ),
        TabItem(
            title = "Training History",
            screen = { TrainingHistoryTab(workouts) }
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
