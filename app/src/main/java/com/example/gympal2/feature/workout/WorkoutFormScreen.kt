package com.example.gympal2.feature.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gympal2.core.ui.general.AppTextField
import com.example.gympal2.core.ui.general.ErrorText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun WorkoutFormScreen(
    state: StateFlow<WorkoutFormStateHolder.WorkoutFormState>,
    currentGymId: String
) {
    val screenState = state.collectAsState()
    println("currentGymId: $currentGymId")
    LaunchedEffect(key1 = currentGymId) {
        screenState.value.setGymId(currentGymId)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxSize()
    ) {

        screenState.value.name.run {
            AppTextField(
                value = value,
                onValueChange = onValueChange,
                label = "Name",
                isError = !error.isValid && !error.errorMessage.isNullOrBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            if (!error.isValid && !error.errorMessage.isNullOrBlank()) {
                ErrorText(text = error.errorMessage!!)
            }
        }


        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            itemsIndexed(screenState.value.exercises) { index, item ->
                AddExerciseForm(
                    exercise = item,
                    onRemove = { screenState.value.removeExercise(index) },
                    index = index
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { screenState.value.addExercise() },
            ) {
                Text(text = "Add Exercise")
            }

            Button(
                onClick = {
                    println("Workout form screen state Values are: ${screenState.value.name}, ${screenState.value.exercises}")
                    screenState.value.onSubmitClicked()
                },
            ) {
                Text("Submit")
            }
        }
    }

}

@Composable
fun AddExerciseForm(
    exercise: ExerciseFormState,
    onRemove: () -> Unit,
    index: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        exercise.name.run {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                AppTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = "Exercise Name ${index + 1}",
                    isError = !error.isValid && !error.errorMessage.isNullOrBlank(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Remove-Exercise",
                        tint = Color.Red
                    )
                }
            }

            if (!error.isValid && !error.errorMessage.isNullOrBlank()) {
                ErrorText(text = error.errorMessage!!)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        exercise.description.run {
            AppTextField(
                value = value,
                onValueChange = onValueChange,
                label = "Exercise Description ${index + 1}",
                isError = !error.isValid && !error.errorMessage.isNullOrBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            if (!error.isValid && !error.errorMessage.isNullOrBlank()) {
                ErrorText(text = error.errorMessage!!)
            }
        }
    }
}