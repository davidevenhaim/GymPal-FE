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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gympal2.R
import com.example.gympal2.core.ui.general.AppTextField
import com.example.gympal2.core.ui.general.ErrorText
import com.example.gympal2.core.ui.general.LottieAnimationView
import org.koin.androidx.compose.koinViewModel

@Composable
fun WorkoutFormScreen(currentGymId: String, navController: NavHostController) {
    val workoutViewModel: WorkoutViewModel = koinViewModel()
    val workoutFormStateHolder = remember { WorkoutFormStateHolderImpl(workoutViewModel) }

    val screenState = workoutFormStateHolder.state.collectAsState()

    LaunchedEffect(key1 = currentGymId) {
        screenState.value.setGymId(currentGymId)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxSize()
    ) {

        LottieAnimationView(
            animation = R.raw.workout1,
            modifier = Modifier
                .size(350.dp)
                .padding(12.dp)
                .fillMaxWidth(),
        )

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
                    if (screenState.value.onSubmitClicked()) {
                        navController.navigateUp()
                    }
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