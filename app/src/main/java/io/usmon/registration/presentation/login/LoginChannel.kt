package io.usmon.registration.presentation.login

import io.usmon.registration.util.UiText

// Created by Usmon Abdurakhmanv on 6/9/2022.

sealed class LoginChannel {
    object Register : LoginChannel()
    object Success : LoginChannel()
    data class ShowSnackbar(val message: UiText) : LoginChannel()
}
