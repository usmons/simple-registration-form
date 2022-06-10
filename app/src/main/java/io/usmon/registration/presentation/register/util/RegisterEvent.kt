package io.usmon.registration.presentation.register.util

import android.graphics.Bitmap

// Created by Usmon Abdurakhmanv on 6/10/2022.

sealed class RegisterEvent {
    class ImageChanged(val image: Bitmap) : RegisterEvent()
    class FullNameChanged(val fullName: String) : RegisterEvent()
    class PhoneNumberChanged(val phoneNumber: String) : RegisterEvent()
    class CountryChanged(val country: Int) : RegisterEvent()
    class AddressChanged(val address: String) : RegisterEvent()
    class PasswordChanged(val password: String) : RegisterEvent()
    object Register : RegisterEvent()
}