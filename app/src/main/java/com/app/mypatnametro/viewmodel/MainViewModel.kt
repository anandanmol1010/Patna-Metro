package com.app.mypatnametro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mypatnametro.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val metroRepository: MetroRepository
) : ViewModel() {
    
    private val _appState = MutableStateFlow(AppState.LAUNCH)
    val appState: StateFlow<AppState> = _appState.asStateFlow()
    
    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()
    
    fun onLaunchComplete() {
        viewModelScope.launch {
            // Check if onboarding was completed
            // For now, skip onboarding and go directly to main
            _appState.value = AppState.MAIN
        }
    }
    
    fun onOnboardingComplete() {
        viewModelScope.launch {
            _isOnboardingCompleted.value = true
            _appState.value = AppState.MAIN
        }
    }
    
    fun skipOnboarding() {
        _appState.value = AppState.MAIN
    }
}

enum class AppState {
    LAUNCH,
    ONBOARDING,
    MAIN
}
