package com.example.gympal2.feature.map.GymBottomSheet

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gympal2.R
import com.example.gympal2.core.ui.general.LottieWithText
import com.example.gympal2.feature.workout.Exercise
import com.example.gympal2.feature.workout.Workout

@Composable
fun WorkoutCardList(workouts: List<Workout>, isOnline: Boolean) {
    Column {
        if (workouts.isEmpty()) {
            EmptyWorkoutList(isOnline)
        } else {
            workouts.forEach { (name, exercises) ->
                WorkoutCard(name = name, exercises = exercises)
            }
        }
    }
}

@Composable
fun EmptyWorkoutList(isOnline: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LottieWithText(
            animation = R.raw.not_found,
            modifier = Modifier
                .size(200.dp)
                .padding(12.dp),
            text = if (isOnline) "You currently doesn't have any workout with the selected gym!" else "No internet connection."
        )
    }
}

@Composable
fun WorkoutCard(name: String, exercises: List<Exercise>) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Workout Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand Icon"
                    )
                }
            }

            if (expanded) {

                exercises.forEachIndexed { index, exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Exercise Icon",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${index + 1}. ${exercise.name}",
                            fontSize = 16.sp
                        )
                        Text(
                            text = "${index + 1}. ${exercise.description}",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}


