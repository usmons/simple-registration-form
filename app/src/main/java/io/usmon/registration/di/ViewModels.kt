package io.usmon.registration.di

import io.usmon.registration.presentation.login.LoginViewModel
import io.usmon.registration.presentation.register.RegisterViewModel
import io.usmon.registration.presentation.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Created by Usmon Abdurakhmanv on 6/9/2022.

val viewModels = module {

    viewModel {
        LoginViewModel(get(), get(), get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }

    viewModel {
        UsersViewModel(get(), get(), get())
    }
}