package io.usmon.registration.domain.model.wrapper

import io.usmon.registration.data.model.local.entity.UserEntity
import io.usmon.registration.domain.model.User

// Created by Usmon Abdurakhmanv on 6/9/2022.

fun User.toUserEntity(): UserEntity = UserEntity(
    fullName = fullName,
    country = country,
    address = address,
    password = password,
    image = image,
    phoneNumber = phoneNumber
)

fun UserEntity.toUser(): User = User(
    fullName = fullName,
    country = country,
    address = address,
    password = password,
    image = image,
    phoneNumber = phoneNumber
)

