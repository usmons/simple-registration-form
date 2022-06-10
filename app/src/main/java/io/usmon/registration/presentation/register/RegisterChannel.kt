package io.usmon.registration.presentation.register

import io.usmon.registration.util.UiText

// Created by Usmon Abdurakhmanv on 6/9/2022.

sealed class RegisterChannel {
    object Success : RegisterChannel()
    data class ShowSnackbar(val message: UiText) : RegisterChannel()
}
