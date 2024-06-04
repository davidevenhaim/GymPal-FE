package com.example.gympal2.ui.screen.main.Map.GymBottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gympal2.model.Exercise
import com.example.gympal2.model.Gym
import com.example.gympal2.model.WorkoutFormState
import com.example.gympal2.ui.screen.auth.ErrorText
import com.example.gympal2.ui.shared.AppTextField
import com.example.gympal2.util.ValidationResult
import com.example.gympal2.util.validateWorkoutForm

@Composable
fun GymInfoTab(gym: Gym) {
    var isCreatingWorkout by remember { mutableStateOf(false) }
    if (isCreatingWorkout) {
        ExerciseForm { submittedFormState ->
            // Handle form submission
            println("Submitted form is: $submittedFormState")
        }
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

@Composable
fun ExerciseForm(onFormSubmit: (WorkoutFormState) -> Unit) {
    var formState by remember { mutableStateOf(WorkoutFormState(exercises = MutableList(1) { Exercise() })) }
    val nameError by remember { mutableStateOf<ValidationResult?>(null) }
    val exerciseErrors by remember { mutableStateOf<Map<String, ValidationResult>?>(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxSize()
    ) {
        AppTextField(
            value = formState.name,
            onValueChange = { formState = formState.copy(name = it) },
            label = "Name",
            isError = nameError?.isValid == false,
            modifier = Modifier.fillMaxWidth()
        )
        if (nameError?.isValid == false) {
            ErrorText(text = nameError?.errorMessage!!)
        }

        formState.exercises.forEachIndexed { index, exercise ->

            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(
                value = exercise.name,
                onValueChange = { exercise.name = it },
                label = "Exercise Name",
                isError = exerciseErrors?.get("exerciseName$index")?.isValid == false,
                modifier = Modifier.fillMaxWidth()
            )
            if (exerciseErrors?.get("exerciseName$index")?.isValid == false) {
                ErrorText(text = exerciseErrors!!["exerciseName$index"]?.errorMessage!!)
            }

            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(
                value = exercise.description,
                onValueChange = { exercise.description = it },
                label = "Exercise Description",
                isError = exerciseErrors?.get("exerciseDescription$index")?.isValid == false,
                modifier = Modifier.fillMaxWidth()
            )
            if (exerciseErrors?.get("exerciseDescription$index")?.isValid == false) {
                ErrorText(text = exerciseErrors!!["exerciseName$index"]?.errorMessage!!)
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val validationErrors = validateWorkoutForm(formState)
                if (validationErrors.values.all { it.isValid }) {
                    onFormSubmit(formState)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}