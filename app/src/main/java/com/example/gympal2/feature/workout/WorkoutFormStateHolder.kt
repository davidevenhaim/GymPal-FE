package com.example.gympal2.feature.workout

import com.example.gympal2.core.ui.form.ValidationResult
import com.example.gympal2.core.ui.form.validateString
import com.example.gympal2.util.FormField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class ExerciseFormState(
    val name: FormField,
    val description: FormField,
    val key: UUID
)

interface WorkoutFormStateHolder {
    data class WorkoutFormState(
        val name: FormField,
        val exercises: MutableList<ExerciseFormState>,
        val gym: String,
        val onSubmitClicked: () -> Boolean,
        val addExercise: () -> Unit,
        val removeExercise: (Int) -> Unit,
        val setGymId: (String) -> Unit
    )

    val state: StateFlow<WorkoutFormState>
}


class WorkoutFormStateHolderImpl(private val workoutViewModel: WorkoutViewModel) :
    WorkoutFormStateHolder {

    private val mutableState: MutableStateFlow<WorkoutFormStateHolder.WorkoutFormState> =
        MutableStateFlow(
            WorkoutFormStateHolder.WorkoutFormState(
                name = createNameField(),
                exercises = createExercisesForm(),
                gym = "",
                onSubmitClicked = ::onSubmitClicked,
                addExercise = ::addExercise,
                removeExercise = ::removeExercise,
                setGymId = ::setGymId
            )
        )

    override val state: StateFlow<WorkoutFormStateHolder.WorkoutFormState>
        get() = mutableState

    fun setGymId(gymId: String) {
        mutableState.update {
            it.copy(
                gym = gymId
            )
        }
    }

    fun addExercise() {
        val key = UUID.randomUUID()
        mutableState.update {
            it.copy(
                exercises = (it.exercises + createExerciseFormField()).toMutableList()
            )
        }
    }

    fun removeExercise(index: Int) {
        mutableState.update {
            it.copy(
                exercises = it.exercises.toMutableList().apply { removeAt(index) }
            )
        }
    }

    private fun onSubmitClicked(): Boolean {
        state.value.run {
            if (isFormValid()) {
                workoutViewModel.createWorkout(
                    Workout(
                        name = name.value,
                        exercises = exercises.map {
                            Exercise(
                                name = it.name.value,
                                description = it.description.value
                            )
                        }.toMutableList(),
                        gym = gym
                    )
                )
                return true
            }
        }
        return false
    }

    private fun isFormValid(): Boolean {
        mutableState.update {
            it.copy(
                exercises = it.exercises.map { exercise ->
                    exercise.copy(
                        description = exercise.description.copy(error = validateString(exercise.description.value)),
                        name = exercise.name.copy(error = validateString(exercise.name.value))
                    )
                }.toMutableList(),
                name = it.name.copy(error = validateString(it.name.value)),
            )
        }

        state.value.run {
            return name.error.isValid && exercises.all { it.name.error.isValid && it.description.error.isValid }
        }
    }


    private fun createExercisesForm(): MutableList<ExerciseFormState> {
        return mutableListOf(createExerciseFormField())
    }

    private fun createNameField() = FormField(
        value = "",
        error = ValidationResult(false),
        onValueChange = { newValue ->
            mutableState.update {
                it.copy(
                    name = it.name.copy(
                        value = newValue,
                        error = validateString(newValue)
                    )
                )
            }
        },
    )

    private fun createExerciseDescField(key: UUID) = FormField(
        value = "",
        error = ValidationResult(false),
        onValueChange = { newValue ->
            val index = state.value.exercises.indexOfFirst { it.key == key }
            if (index != -1) {
                mutableState.update {
                    it.copy(
                        exercises = it.exercises.map { exercise ->
                            if (exercise.key == key) {
                                exercise.copy(
                                    description = exercise.description.copy(
                                        value = newValue,
                                        error = validateString(newValue)
                                    ),
                                )
                            } else exercise

                        }.toMutableList(),
                    )
                }
            }
        },
    )

    private fun createExerciseNameField(key: UUID) = FormField(
        value = "",
        error = ValidationResult(false),
        onValueChange = { newValue ->
            val index = state.value.exercises.indexOfFirst { it.key == key }
            if (index != -1) {
                mutableState.update {
                    it.copy(
                        exercises = it.exercises.map { exercise ->
                            if (exercise.key == key) {
                                exercise.copy(
                                    name = exercise.name.copy(
                                        value = newValue,
                                        error = validateString(newValue)
                                    ),
                                )
                            } else exercise

                        }.toMutableList(),
                    )
                }
            }
        },
    )

    private fun createExerciseFormField(): ExerciseFormState {
        val key = UUID.randomUUID()

        return ExerciseFormState(
            description = createExerciseDescField(key),
            name = createExerciseNameField(key),
            key = key
        )
    }

}

fun validateWorkout(exercises: MutableList<ExerciseFormState>): MutableList<ExerciseFormState> {
    return exercises.map { exercise ->
//        name = it.name.copy(error = validateString(it.name.value)),
        exercise.copy(
//            name = exercise.name.copy { exercise.name.error = validateString(exercise.name.value) },
            description = exercise.description.copy {
                exercise.description.error = validateString(exercise.description.value)
            },

            )
    }.toMutableList()
}
