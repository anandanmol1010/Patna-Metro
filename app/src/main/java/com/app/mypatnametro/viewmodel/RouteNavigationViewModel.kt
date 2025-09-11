package com.app.mypatnametro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mypatnametro.data.model.MetroNetwork
import com.app.mypatnametro.data.model.MetroStation
import com.app.mypatnametro.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteNavigationViewModel @Inject constructor(
    private val metroRepository: MetroRepository
) : ViewModel() {
    
    private val metroNetwork = MetroNetwork()
    
    private val _uiState = MutableStateFlow(RouteNavigationUiState())
    val uiState: StateFlow<RouteNavigationUiState> = _uiState.asStateFlow()
    
    // Station list matching iOS implementation
    private val stations = listOf(
        "Danapur Cantonment", "Saguna Mor", "RPS Mor", "Patliputra", "Rukanpura",
        "Raja Bazar", "Patna Zoo", "Vikas Bhawan", "Vidyut Bhawan", "Patna Junction",
        "CNLU", "Mithapur", "Ramkrishna Nagar", "Jaganpur", "Khemni Chak", "Akashvani",
        "Gandhi Maidan", "PMCH", "Patna University", "Moin Ul Haq Stadium", "Rajendra Nagar",
        "Malahi Pakri", "Bhoothnath", "Zero Mile", "New ISBT"
    )
    
    init {
        _uiState.value = _uiState.value.copy(stations = stations)
    }
    
    fun updateSource(source: String) {
        _uiState.value = _uiState.value.copy(source = source)
    }
    
    fun updateDestination(destination: String) {
        _uiState.value = _uiState.value.copy(destination = destination)
    }
    
    fun toggleSourceDropdown(show: Boolean) {
        _uiState.value = _uiState.value.copy(showSourceDropdown = show)
    }
    
    fun toggleDestinationDropdown(show: Boolean) {
        _uiState.value = _uiState.value.copy(showDestinationDropdown = show)
    }
    
    fun findRoute() {
        val currentState = _uiState.value
        if (currentState.source.isNotEmpty() && currentState.destination.isNotEmpty()) {
            _uiState.value = currentState.copy(isLoading = true)
            
            // Use MetroNetwork's BFS algorithm like iOS implementation
            val foundRoute = metroNetwork.findShortestPath(currentState.source, currentState.destination)
            
            _uiState.value = _uiState.value.copy(
                route = foundRoute ?: listOf("No route found"),
                showRouteResults = foundRoute != null,
                isLoading = false
            )
        }
    }
    
    fun clearRoute() {
        _uiState.value = _uiState.value.copy(
            source = "",
            destination = "",
            route = emptyList(),
            showRouteResults = false,
            showSourceDropdown = false,
            showDestinationDropdown = false
        )
    }
}

/**
 * UI state for Route Navigation screen
 */
data class RouteNavigationUiState(
    val source: String = "",
    val destination: String = "",
    val stations: List<String> = emptyList(),
    val route: List<String> = emptyList(),
    val showSourceDropdown: Boolean = false,
    val showDestinationDropdown: Boolean = false,
    val showRouteResults: Boolean = false,
    val isLoading: Boolean = false
)
