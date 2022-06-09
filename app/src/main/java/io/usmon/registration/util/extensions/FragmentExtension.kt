package io.usmon.registration.util.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Created by Usmon Abdurakhmanv on 6/10/2022.

fun <T> Fragment.collect(stateFlow: StateFlow<T>, run: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            stateFlow.collect {
                run(it)
            }
        }
    }
}

fun <T> Fragment.collect(flow: Flow<T>, run: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                run(it)
            }
        }
    }
}