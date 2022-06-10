package io.usmon.registration.presentation.login.util

// Created by Usmon Abdurakhmanv on 6/9/2022.

sealed class LoginEvent {
    data class PhoneNumberChanged(val phoneNumber: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Register : LoginEvent()
    object Login : LoginEvent()
}
