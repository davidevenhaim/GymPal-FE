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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gympal2.core.ui.general.AppTextField

@Composable
fun WorkoutForm(onFormSubmit: (WorkoutFormState) -> Unit) {
    var formState by remember {
        mutableStateOf(
            WorkoutFormState(
                name = "",
                exercises = MutableList(1) { Exercise("", "") })
        )
    }
    var workout by remember { mutableStateOf(Workout()) }

    fun addExercise() {
        val exercises = workout.exercises + Exercise()
        workout = workout.copy(exercises = exercises)
    }

    fun removeExercise(index: Int) {
        workout = workout.copy(
            exercises = workout.exercises.toMutableList().apply { removeAt(index) }
        )
    }

    fun updateExercise(index: Int, updatedExercise: Exercise) {
        workout = workout.copy(
            exercises = workout.exercises.toMutableList().apply { this[index] = updatedExercise }
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .fillMaxSize()
    ) {

        AppTextField(
            value = workout.name,
            onValueChange = { workout = workout.copy(name = it) },
            label = "Name",
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            itemsIndexed(workout.exercises) { index, item ->
                AddExerciseForm(
                    exercise = item,
                    onRemove = { removeExercise(index) },
                    onNameChange = { newName ->
                        updateExercise(index, item.copy(name = newName))
                    },
                    onDescriptionChange = { newDescription ->
                        updateExercise(index, item.copy(description = newDescription))
                    },
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
                onClick = { addExercise() },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Transparent, // Background color of the button
//                    contentColor = Color.Black   // Content color (text or icon color)
//                ),
//                modifier = Modifier.border(
//                    BorderStroke(2.dp, Color.Black), // Border width and color
//                    shape = RoundedCornerShape(16.dp)
//                )
            ) {
                Text(text = "Add Exercise")

            }

            Button(
                onClick = {
                    println("Values: ${workout.name}, ${workout.exercises}")
//                    return
//                    val validationErrors = validateWorkoutForm(formState)
//                    if (validationErrors.values.all { it.isValid }) {
//                        onFormSubmit(formState)
//                    }
                },
            ) {
                Text("Submit")
            }
        }
    }

}

@Composable
fun AddExerciseForm(
    exercise: Exercise,
    onRemove: () -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    index: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            AppTextField(
                value = exercise.name,
                onValueChange = onNameChange,
                label = "Exercise Name ${index + 1}",
//        isError = exerciseErrors?.get("exerciseName$index")?.isValid == false,
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

//    if (exerciseErrors?.get("exerciseName$index")?.isValid == false) {
//        ErrorText(text = exerciseErrors!!["exerciseName$index"]?.errorMessage!!)
//    }
        Spacer(modifier = Modifier.height(8.dp))

        AppTextField(
            value = exercise.description,
            onValueChange = onDescriptionChange,
            label = "Exercise Description ${index + 1}",
//        isError = exerciseErrors?.get("exerciseDescription$index")?.isValid == false,
            modifier = Modifier.fillMaxWidth()
        )
//    if (exerciseErrors?.get("exerciseDescription$index")?.isValid == false) {
//        ErrorText(text = exerciseErrors!!["exerciseName$index"]?.errorMessage!!)
//    }
    }
}