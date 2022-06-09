package io.usmon.registration.domain.model

import android.graphics.Bitmap

// Created by Usmon Abdurakhmanv on 6/9/2022.

data class User(
    val fullName: String,
    val country: Int,
    val address: String,
    val password: Int,
    var image: Bitmap?,
    val phoneNumber: String,
)
