package com.app.mypatnametro.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.app.mypatnametro.data.model.UserProfile
import com.app.mypatnametro.data.repository.UserProfileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userProfileManager = UserProfileManager(application.applicationContext)
    
    var profile = mutableStateOf(
        userProfileManager.loadUserProfile() ?: userProfileManager.getDefaultProfile()
    )
        private set
    
    private val _userProfile = MutableStateFlow(
        userProfileManager.loadUserProfile() ?: userProfileManager.getDefaultProfile()
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()
    
    fun updateProfile(newProfile: UserProfile) {
        profile.value = newProfile
        _userProfile.value = newProfile
    }
    
    fun saveProfile() {
        userProfileManager.saveUserProfile(profile.value)
        _userProfile.value = profile.value
    }
    
    fun loadProfile() {
        val loadedProfile = userProfileManager.loadUserProfile()
        if (loadedProfile != null) {
            profile.value = loadedProfile
            _userProfile.value = loadedProfile
        }
    }
    
    fun updateFullName(name: String) {
        val updatedProfile = profile.value.copy(fullName = name)
        profile.value = updatedProfile
        _userProfile.value = updatedProfile
    }
    
    fun updateGender(gender: String) {
        val updatedProfile = profile.value.copy(gender = gender)
        profile.value = updatedProfile
        _userProfile.value = updatedProfile
    }
    
    fun updateBirthday(birthday: Date) {
        val updatedProfile = profile.value.copy(birthday = birthday)
        profile.value = updatedProfile
        _userProfile.value = updatedProfile
    }
    
    fun updatePhoneNumber(phoneNumber: String) {
        val updatedProfile = profile.value.copy(phoneNumber = phoneNumber)
        profile.value = updatedProfile
        _userProfile.value = updatedProfile
    }
    
    fun updateEmail(email: String) {
        val updatedProfile = profile.value.copy(email = email)
        profile.value = updatedProfile
        _userProfile.value = updatedProfile
    }
    
    fun updateMetroCardNumber(cardNumber: String) {
        val updatedProfile = profile.value.copy(metroCardNumber = cardNumber)
        profile.value = updatedProfile
        _userProfile.value = updatedProfile
    }
}
