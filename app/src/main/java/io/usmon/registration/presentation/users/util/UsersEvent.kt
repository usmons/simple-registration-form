package io.usmon.registration.presentation.users.util

import io.usmon.registration.domain.model.User

// Created by Usmon Abdurakhmanv on 6/10/2022.

sealed class UsersEvent {
    data class OpenDialog(val user: User) : UsersEvent()
    object CloseDialog : UsersEvent()
    object LogOut : UsersEvent()
}
