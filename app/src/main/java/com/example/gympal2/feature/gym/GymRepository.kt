package com.example.gympal2.feature.gym

import NetworkUtil
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.gympal2.core.localDB.DatabaseProvider
import com.example.gympal2.core.localDB.GymDao
import com.example.gympal2.core.localDB.OfflineRequestDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GymRepository(
    private val apiService: GymService,
    context: Context,
    private val networkUtil: NetworkUtil
) {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val _gyms = MutableStateFlow<List<Gym>>(emptyList())
    val gyms: StateFlow<List<Gym>> get() = _gyms
    
    private val gymDao: GymDao = DatabaseProvider.getDatabase(context).gymDao()
    private val offlineRequestDao: OfflineRequestDao =
        DatabaseProvider.getDatabase(context).offlineRequestDao()

    init {
        observeNetworkChanges()
    }


    suspend fun fetchGyms() {
        println("Is online : ${networkUtil.isOnline()}")
        if (networkUtil.isOnline()) {
            val currentGyms = apiService.getAllGyms()
            gymDao.insertGyms(currentGyms)
            _gyms.value = currentGyms
        } else {
            _gyms.value = gymDao.getAllGyms()
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