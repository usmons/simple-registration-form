package io.usmon.registration.util.preferences

// Created by Usmon Abdurakhmanv on 6/9/2022.

interface Preferences {

    fun isUserAuthenticated(): Boolean

    fun registerUser(phoneNumber: String)

    fun getUser(): String?

    fun clear()

    companion object {
        const val PHONE_NUMBER = "username"
    }
}