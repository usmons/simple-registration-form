package io.usmon.registration.data.model.local.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

// Created by Usmon Abdurakhmanv on 6/9/2022.

@Entity(tableName = "users")
data class UserEntity(
    val fullName: String,
    val country: Int,
    val address: String,
    val password: Int,
    var image: Bitmap?,
    @PrimaryKey(autoGenerate = false) val phoneNumber: String,
)
