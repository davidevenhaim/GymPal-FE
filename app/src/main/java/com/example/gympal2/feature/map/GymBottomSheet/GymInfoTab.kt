package com.example.gympal2.feature.map.GymBottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.feature.workout.WorkoutFormScreen
import com.example.gympal2.feature.workout.WorkoutFormStateHolder
import kotlinx.coroutines.flow.StateFlow


@Composable
fun GymInfoTab(gym: Gym, screenState: StateFlow<WorkoutFormStateHolder.WorkoutFormState>) {

    var isCreatingWorkout by remember { mutableStateOf(false) }

    if (isCreatingWorkout) {
        WorkoutFormScreen(state = screenState, currentGymId = gym.id)
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            gym.run {
                Column(modifier = Modifier.padding(10.dp)) {

                    WorkHoursTable(workingHours = workingHours)

                    Button(
                        onClick = { isCreatingWorkout = true },
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Text(text = "New Workout")
                    }
                }
            }

        }
    }
}
