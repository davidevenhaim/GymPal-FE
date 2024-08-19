package com.example.gympal2.feature.gym

import NetworkUtil
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class GymViewModel(private val gymRepository: GymRepository, private val networkUtil: NetworkUtil) :
    ViewModel(),
    KoinComponent {
    init {
        fetchGyms()
        observeNetworkChanges()
    }

    fun getGyms(): StateFlow<List<Gym>> = gymRepository.gyms

    private fun fetchGyms() {
        viewModelScope.launch {
            try {
                while (true) {
                    gymRepository.fetchGyms()
                    delay(GYM_POLLING_DELAY.toLong())
                    if (!networkUtil.isOnlineState.value) break
                }
            } catch (e: Exception) {
                println("Error encountered! $e")
            }
        }
    }

    private fun observeNetworkChanges() {
        networkUtil.registerNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModelScope.launch {
                    fetchGyms()
                }
            }
        })
    }
}