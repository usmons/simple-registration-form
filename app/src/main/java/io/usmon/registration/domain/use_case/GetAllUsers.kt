package io.usmon.registration.domain.use_case

import io.usmon.registration.domain.model.User
import io.usmon.registration.domain.model.wrapper.toUser
import io.usmon.registration.domain.repository.UserRepository
import io.usmon.registration.util.preferences.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

// Created by Usmon Abdurakhmanv on 6/9/2022.

class GetAllUsers(
    private val repository: UserRepository,
    private val preferences: Preferences
) {

    operator fun invoke(): Flow<List<User>> {
        val phoneNumber = preferences.getUser() ?: return flow { }
        return repository
            .getAllUsers()
            .map { users ->
                users.filter {
                    it.phoneNumber != phoneNumber
                }.map { userEntity ->
                    userEntity.toUser()
                }
            }
    }
}