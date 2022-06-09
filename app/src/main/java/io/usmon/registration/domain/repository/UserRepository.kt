package io.usmon.registration.domain.repository

import io.usmon.registration.data.model.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

// Created by Usmon Abdurakhmanv on 6/9/2022.

interface UserRepository {

    suspend fun insertUser(user: UserEntity)

    suspend fun getUserByPhoneNumber(phoneNumber: String): UserEntity?

    fun getAllUsers(): Flow<List<UserEntity>>
}