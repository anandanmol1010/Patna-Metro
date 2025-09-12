package com.app.mypatnametro.data.model

import java.util.Date

data class UserProfile(
    val fullName: String = "Guest",
    val gender: String = "Male",
    val birthday: Date = Date(),
    val phoneNumber: String = "",
    val email: String = "",
    val metroCardNumber: String = "0000000000"
)
