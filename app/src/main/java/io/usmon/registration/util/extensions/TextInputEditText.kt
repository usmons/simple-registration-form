package io.usmon.registration.util.extensions

import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

// Created by Usmon Abdurakhmanv on 6/10/2022.

fun TextInputEditText.update(text: String) {
    setText("")
    append(text)
}

fun TextInputEditText.onChangedListener(changed: (String) -> Unit) {
    addTextChangedListener {
        changed(text.toString())
    }
}