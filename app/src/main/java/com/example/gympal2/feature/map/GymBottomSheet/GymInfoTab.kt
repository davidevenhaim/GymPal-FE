package com.example.gympal2.feature.map.GymBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gympal2.feature.workout.Exercise
import com.example.gympal2.feature.gym.Gym
import com.example.gympal2.feature.workout.WorkoutFormState
import com.example.gympal2.core.ui.general.ErrorText
import com.example.gympal2.core.ui.general.AppTextField
import com.example.gympal2.core.ui.form.ValidationResult
import com.example.gympal2.core.ui.form.validateWorkoutForm

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
    var formState by remember { mutableStateOf(WorkoutFormState(name="",exercises = MutableList(2) { Exercise("", "") })) }
    val nameError by remember { mutableStateOf<ValidationResult?>(null) }
    val exerciseErrors by remember { mutableStateOf<Map<String, ValidationResult>?>(null) }

    fun addExercise() {
//        formState.exercises.add(Exercise("", ""))
    }

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
            AddExerciseForm(exercise, index)
        }



        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = {addExercise()}) {
                Text(text = "Add Exercise")
            }

            Button(
                onClick = {
                    val validationErrors = validateWorkoutForm(formState)
                    if (validationErrors.values.all { it.isValid }) {
                        onFormSubmit(formState)
                    }
                },
            ) {
                Text("Submit")
            }
        }
    }

}

@Composable
fun AddExerciseForm(exercise: Exercise, index: Int) {
    Spacer(modifier = Modifier.height(8.dp))

    AppTextField(
        value = exercise.name,
        onValueChange = { exercise.name = it },
        label = "Exercise Name",
//        isError = exerciseErrors?.get("exerciseName$index")?.isValid == false,
        modifier = Modifier.fillMaxWidth()
    )

//    if (exerciseErrors?.get("exerciseName$index")?.isValid == false) {
//        ErrorText(text = exerciseErrors!!["exerciseName$index"]?.errorMessage!!)
//    }

    AppTextField(
        value = exercise.description,
        onValueChange = { exercise.description = it },
        label = "Exercise Description",
//        isError = exerciseErrors?.get("exerciseDescription$index")?.isValid == false,
        modifier = Modifier.fillMaxWidth()
    )
//    if (exerciseErrors?.get("exerciseDescription$index")?.isValid == false) {
//        ErrorText(text = exerciseErrors!!["exerciseName$index"]?.errorMessage!!)
//    }
}
