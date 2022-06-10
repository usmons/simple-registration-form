package io.usmon.registration.presentation.register

import android.graphics.Bitmap
import android.net.Uri

// Created by Usmon Abdurakhmanv on 6/10/2022.

sealed class RegisterUiEvent {
    class ImageChanged(val image: Bitmap) : RegisterUiEvent()
    class FullNameChanged(val fullName: String) : RegisterUiEvent()
    class PhoneNumberChanged(val phoneNumber: String) : RegisterUiEvent()
    class CountryChanged(val country: Int) : RegisterUiEvent()
    class AddressChanged(val address: String) : RegisterUiEvent()
    class PasswordChanged(val password: String) : RegisterUiEvent()
    object Register : RegisterUiEvent()
}