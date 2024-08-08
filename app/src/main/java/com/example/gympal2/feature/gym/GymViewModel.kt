package com.example.gympal2.feature.gym

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class GymViewModel(private val repository: GymRepository) : ViewModel(), KoinComponent {
    private val _gyms = MutableStateFlow<List<Gym>>(emptyList())
    val gyms: StateFlow<List<Gym>> get() = _gyms

    init {
        fetchGyms()
    }

    private fun fetchGyms() {
        viewModelScope.launch {
            try {
                while (true) {
                    _gyms.value = repository.getGyms()
                    delay(GYM_POLLING_DELAY.toLong())
                }
            } catch (e: Exception) {
                println("Error encountered! $e")
            }
        }
    }
}