package com.app.mypatnametro.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mypatnametro.data.repository.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val metroRepository: MetroRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    
    private val _appState = MutableStateFlow(AppState.LAUNCH)
    val appState: StateFlow<AppState> = _appState.asStateFlow()
    
    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()
    
    private val sharedPrefs = context.getSharedPreferences("patna_metro_prefs", Context.MODE_PRIVATE)
    
    fun onLaunchComplete() {
        viewModelScope.launch {
            // Check if disclaimer was accepted
            val disclaimerAccepted = sharedPrefs.getBoolean("disclaimer_accepted", false)
            if (disclaimerAccepted) {
                _appState.value = AppState.MAIN
            } else {
                _appState.value = AppState.DISCLAIMER
            }
        }
    }
    
    fun onDisclaimerAccepted() {
        viewModelScope.launch {
            // Save disclaimer acceptance
            sharedPrefs.edit().putBoolean("disclaimer_accepted", true).apply()
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
    
    fun isDisclaimerAccepted(): Boolean {
        return sharedPrefs.getBoolean("disclaimer_accepted", false)
    }
}

enum class AppState {
    LAUNCH,
    ONBOARDING,
    DISCLAIMER,
    MAIN
}
