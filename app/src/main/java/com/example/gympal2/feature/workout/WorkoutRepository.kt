package com.example.gympal2.feature.workout

import NetworkUtil
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.gympal2.core.localDB.DatabaseProvider
import com.example.gympal2.core.localDB.OfflineRequestDao
import com.example.gympal2.core.localDB.OfflineRequestEntity
import com.example.gympal2.util.CREATE_WORKOUT_URL
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorkoutRepository(
    private val apiService: WorkoutService,
    private val networkUtil: NetworkUtil,
    context: Context,
) {
    private val gson = Gson()
    var workouts = MutableStateFlow<List<Workout>>(emptyList())
        private set
    private val scope = CoroutineScope(Dispatchers.Main)

    private val offlineRequestDao: OfflineRequestDao =
        DatabaseProvider.getDatabase(context).offlineRequestDao()

    suspend fun createWorkout(workout: WorkoutData): Boolean {
        if (networkUtil.isOnlineState.value) {
            return apiService.createWorkout(workout)
        } else {
            offlineRequestDao.insertRequest(
                request = OfflineRequestEntity(
                    requestType = CREATE_WORKOUT_URL,
                    payload = gson.toJson(workout)
                )
            )
            return true
        }
    }

    init {
        observeNetworkChanges()
    }

    suspend fun getGymWorkouts(gymId: String) {
        if (networkUtil.isOnlineState.value) {
            scope.launch {
                try {
                    val res = apiService.getGymWorkouts(gymId) ?: listOf()
                    workouts.value = res
                } catch (e: Exception) {
                    workouts.value = listOf()
                    println("Error is: ${e.message}")
                }
            }
        }
    }

    private fun observeNetworkChanges() {
        networkUtil.registerNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                scope.launch {
                    processOfflineRequests()
                }
            }
        })
    }

    private suspend fun processOfflineRequests() {
        if (!networkUtil.isOnlineState.value) return

        val requests = offlineRequestDao.getAllRequests()
        if (requests.isEmpty()) {
            return
        }

        for (request in requests) {
            if (request.requestType == CREATE_WORKOUT_URL) {
                val workoutData: WorkoutData =
                    gson.fromJson(request.payload, WorkoutData::class.java)
                try {
                    val res = apiService.createWorkout(workoutData)
                    if (res) {
                        offlineRequestDao.deleteRequest(request)
                    }
                } catch (e: Exception) {
                    println("Error is: ${e.message}")
                }
            }
        }
    }

}