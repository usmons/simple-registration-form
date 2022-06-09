package io.usmon.registration.domain.use_case

// Created by Usmon Abdurakhmanv on 6/9/2022.

data class UseCases(
    val registerUserUseCase: RegisterUserUseCase,
    val loginUserUseCase: LoginUserUseCase,
    val getAllUsers: GetAllUsers
)
