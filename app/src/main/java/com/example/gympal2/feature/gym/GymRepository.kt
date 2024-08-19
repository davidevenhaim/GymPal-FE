package com.example.gympal2.feature.gym

import NetworkUtil
import android.content.Context
import com.example.gympal2.core.localDB.DatabaseProvider
import com.example.gympal2.core.localDB.GymDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GymRepository(
    private val apiService: GymService,
    context: Context,
    private val networkUtil: NetworkUtil
) {
    private val _gyms = MutableStateFlow<List<Gym>>(emptyList())
    val gyms: StateFlow<List<Gym>> get() = _gyms

    private val gymDao: GymDao = DatabaseProvider.getDatabase(context).gymDao()


    suspend fun fetchGyms() {
        if (networkUtil.isOnlineState.value) {
            val currentGyms = apiService.getAllGyms()
            gymDao.insertGyms(currentGyms)
            _gyms.value = currentGyms
        } else {
            _gyms.value = gymDao.getAllGyms()
        }
    }


}