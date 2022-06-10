package io.usmon.registration.presentation.users.util

import io.usmon.registration.domain.model.User

// Created by Usmon Abdurakhmanv on 6/10/2022.

data class UsersState(
    val users: List<User> = emptyList(),
    val userOnDialog: User? = null
)