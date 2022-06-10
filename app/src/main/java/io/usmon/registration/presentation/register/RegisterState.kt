package io.usmon.registration.presentation.register

import android.graphics.Bitmap

// Created by Usmon Abdurakhmanv on 6/9/2022.

data class RegisterState(
    val image: Bitmap? = null,
    val fullName: String = "",
    val phoneNumber: String = "",
    val country: Int = -1,
    val address: String = "",
    val password: String = ""
)
