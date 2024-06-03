import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gympal2.data.repository.GymRepository
import com.example.gympal2.model.Gym
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
                _gyms.value = repository.getGyms()
            } catch (e: Exception) {
                println("Error encountered! $e")
            }
        }
    }
}