package io.usmon.registration.domain.use_case

import io.usmon.registration.R
import io.usmon.registration.domain.repository.UserRepository
import io.usmon.registration.util.UiText
import io.usmon.registration.util.preferences.Preferences
import java.io.IOException

// Created by Usmon Abdurakhmanv on 6/9/2022.

class LoginUserUseCase(
    private val repository: UserRepository,
    private val preferences: Preferences
) {

    suspend operator fun invoke(
        phoneNumber: String,
        password: String
    ): Result {

        if (phoneNumber.isBlank())
            return Result.Error(message = UiText.StringResource(R.string.phone_empty))

        if (password.isBlank())
            return Result.Error(message = UiText.StringResource(R.string.phone_empty))

        return try {

            val user = repository.getUserByPhoneNumber(phoneNumber)

            if (user == null || user.password != password.hashCode())
                return Result.Error(message = UiText.StringResource(R.string.phone_or_password_error))

            preferences.registerUser(phoneNumber)
            Result.Success
        } catch (e: IOException) {
            Result.Error(message = UiText.StringResource(R.string.something_went_wrong))
        }
    }

    sealed class Result {
        object Success : Result()
        data class Error(val message: UiText) : Result()
    }
}