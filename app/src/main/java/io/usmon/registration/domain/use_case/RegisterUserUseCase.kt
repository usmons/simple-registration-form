package io.usmon.registration.domain.use_case

import android.graphics.Bitmap
import io.usmon.registration.R
import io.usmon.registration.data.model.local.entity.UserEntity
import io.usmon.registration.domain.repository.UserRepository
import io.usmon.registration.util.UiText
import io.usmon.registration.util.preferences.Preferences
import java.io.IOException

// Created by Usmon Abdurakhmanv on 6/9/2022.

class RegisterUserUseCase(
    private val repository: UserRepository,
    private val preferences: Preferences
) {

    suspend operator fun invoke(
        fullName: String,
        phoneNumber: String,
        country: Int,
        address: String,
        password: String,
        image: Bitmap?
    ): Result {

        if (fullName.isBlank())
            return Result.Error(message = UiText.StringResource(R.string.fullname_empty))

        if (phoneNumber.isBlank())
            return Result.Error(message = UiText.StringResource(R.string.phone_empty))

        if (country < 0)
            return Result.Error(message = UiText.StringResource(R.string.country_not_chosen))

        if (address.isBlank())
            return Result.Error(message = UiText.StringResource(R.string.address_empty))

        if (password.length < 8)
            return Result.Error(message = UiText.StringResource(R.string.password_invalid))

        return try {

            if (repository.getUserByPhoneNumber(phoneNumber) != null)
                return Result.Error(message = UiText.StringResource(R.string.user_already_exist))

            val userEntity = UserEntity(
                fullName = fullName,
                phoneNumber = phoneNumber,
                country = country,
                address = address,
                password = password.hashCode(),
                image = image
            )
            repository.insertUser(user = userEntity)
            preferences.registerUser(phoneNumber)
            Result.Success
        } catch (e: IOException) {
            e.printStackTrace()
            Result.Error(message = UiText.StringResource(R.string.something_went_wrong))
        }
    }

    sealed class Result {
        object Success : Result()
        data class Error(public val message: UiText) : Result()
    }
}