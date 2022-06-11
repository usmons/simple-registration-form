package io.usmon.registration.util.preferences

import android.content.SharedPreferences
import io.usmon.registration.util.preferences.Preferences.Companion.PHONE_NUMBER

// Created by Usmon Abdurakhmanv on 6/9/2022.

class PreferencesImpl(
    private val preferences: SharedPreferences
) : Preferences {

    override fun isUserAuthenticated(): Boolean {
        return preferences.getString(PHONE_NUMBER, null) != null
    }

    override fun registerUser(phoneNumber: String) {
        preferences.edit()
            .putString(PHONE_NUMBER, phoneNumber)
            .commit()
    }

    override fun getUser(): String? {
        return preferences.getString(PHONE_NUMBER, null)
    }

    override fun clear() {
        preferences.edit()
            .clear()
            .apply()
    }
}