package com.example.gympal2.feature.workout

import NetworkUtil
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.gympal2.core.localDB.DatabaseProvider
import com.example.gympal2.core.localDB.OfflineRequestDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorkoutRepository(
    private val apiService: WorkoutService,
    private val networkUtil: NetworkUtil,
    context: Context,
) {
    var workouts = MutableStateFlow<List<Workout>>(emptyList())
        private set
    private val scope = CoroutineScope(Dispatchers.Main)

    private val offlineRequestDao: OfflineRequestDao =
        DatabaseProvider.getDatabase(context).offlineRequestDao()

    suspend fun createWorkout(workout: WorkoutData): Boolean {
        println("workout: $workout")
        return apiService.createWorkout(workout)
    }

    init {
        observeNetworkChanges()
    }

    suspend fun getGymWorkouts(gymId: String) {
        scope.launch {
            println("Waiting for Response from gyms is")
            val res = apiService.getGymWorkouts(gymId)
            println("Response from gyms is: $res")
//            if (res != null && res.isNotEmpty()) {
//                workouts.value = res
//            }
        }
    }

    private fun observeNetworkChanges() {
        networkUtil.registerNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                scope.launch {
                    processOfflineRequests()
                }
            }

            override fun onLost(network: Network) {
                // Handle network lost
                println("ASDASDasd network lost")
            }
        })
    }

    private suspend fun processOfflineRequests() {
        val requests = offlineRequestDao.getAllRequests()
        for (request in requests) {
            // Process each request and send to server
            println("Request is: $request")
            // If successful, delete the request from the database
//            offlineRequestDao.deleteRequest(request)
        }
    }

}