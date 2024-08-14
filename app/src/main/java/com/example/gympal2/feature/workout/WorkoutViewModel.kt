package com.example.gympal2.feature.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gympal2.auth.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository,
    private val authRepository: AuthRepository
) :
    ViewModel(),
    KoinComponent {

    fun getWorkouts(): StateFlow<List<Workout>> = workoutRepository.workouts

    fun createWorkout(workout: Workout) {
        val userId = authRepository.getUserId()
        viewModelScope.launch {
            try {
                workoutRepository.createWorkout(
                    WorkoutData(
                        name = workout.name,
                        exercises = workout.exercises,
                        gym = workout.gym,
                        user = userId
                    )
                )
            } catch (e: Exception) {
                println("Error encountered! $e")
            }
        }
    }

    fun getGymWorkouts(gymId: String) {
        viewModelScope.launch {
            try {
                workoutRepository.getGymWorkouts(gymId)
            } catch (e: Exception) {
                println("Error encountered! $e")
            }
        }
    }
}