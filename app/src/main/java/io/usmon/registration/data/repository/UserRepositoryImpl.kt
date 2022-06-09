package io.usmon.registration.data.repository

import io.usmon.registration.data.data_source.UserDao
import io.usmon.registration.data.model.local.entity.UserEntity
import io.usmon.registration.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

// Created by Usmon Abdurakhmanv on 6/9/2022.

class UserRepositoryImpl(
    private val dao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: UserEntity) {
        dao.insertUser(user)
    }

    override suspend fun getUserByPhoneNumber(phoneNumber: String): UserEntity? {
        return dao.getUserByPhoneNumber(phoneNumber)
    }

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return dao.getAllUsers()
    }
}