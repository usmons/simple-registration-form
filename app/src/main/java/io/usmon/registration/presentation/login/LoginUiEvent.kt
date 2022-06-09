package io.usmon.registration.presentation.login

// Created by Usmon Abdurakhmanv on 6/9/2022.

sealed class LoginUiEvent {
    data class PhoneNumberChanged(val phoneNumber: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    object Register : LoginUiEvent()
    object Login : LoginUiEvent()
}
