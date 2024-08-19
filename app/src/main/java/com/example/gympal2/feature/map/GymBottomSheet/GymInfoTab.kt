package com.example.gympal2.feature.map.GymBottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gympal2.feature.gym.Gym


@Composable
fun GymInfoTab(gym: Gym, onNewWorkoutClicked: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {
        gym.run {
            Column(modifier = Modifier.padding(25.dp)) {

                WorkHoursTable(workingHours = workingHours)

                Button(
                    onClick = onNewWorkoutClicked,
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(text = "New Workout")
                }
            }
        }

    }
}
