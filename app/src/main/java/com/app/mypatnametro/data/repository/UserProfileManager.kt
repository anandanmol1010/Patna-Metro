package com.app.mypatnametro.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.app.mypatnametro.data.model.UserProfile
import com.google.gson.Gson
import java.util.Date

class UserProfileManager(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("UserProfilePrefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    companion object {
        private const val USER_PROFILE_KEY = "UserProfileKey"
    }
    
    fun saveUserProfile(profile: UserProfile) {
        val profileJson = gson.toJson(profile)
        sharedPreferences.edit()
            .putString(USER_PROFILE_KEY, profileJson)
            .apply()
        println("Profile saved successfully")
    }
    
    fun loadUserProfile(): UserProfile? {
        val profileJson = sharedPreferences.getString(USER_PROFILE_KEY, null)
        return if (profileJson != null) {
            try {
                gson.fromJson(profileJson, UserProfile::class.java)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }
    
    fun getDefaultProfile(): UserProfile {
        return UserProfile(
            fullName = "Guest",
            gender = "Male",
            birthday = Date(),
            phoneNumber = "",
            email = "",
            metroCardNumber = "0000000000"
        )
    }
}
