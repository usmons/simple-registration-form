package io.usmon.registration.di

import android.content.Context
import androidx.room.Room
import io.usmon.registration.coroutine.DefaultDispatchers
import io.usmon.registration.coroutine.DefaultDispatchersImpl
import io.usmon.registration.data.data_source.UserDatabase
import io.usmon.registration.data.repository.UserRepositoryImpl
import io.usmon.registration.domain.repository.UserRepository
import io.usmon.registration.domain.use_case.GetAllUsers
import io.usmon.registration.domain.use_case.LoginUserUseCase
import io.usmon.registration.domain.use_case.RegisterUserUseCase
import io.usmon.registration.domain.use_case.UseCases
import io.usmon.registration.util.Constants.DB_NAME
import io.usmon.registration.util.Constants.PREFERENCES
import io.usmon.registration.util.preferences.Preferences
import io.usmon.registration.util.preferences.PreferencesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// Created by Usmon Abdurakhmanv on 6/9/2022.

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            DB_NAME
        ).build()
    }

    single<UserRepository> {
        UserRepositoryImpl(dao = get<UserDatabase>().dao)
    }

    single<Preferences> {
        val preferences = androidContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        PreferencesImpl(preferences)
    }

    single {
        UseCases(
            registerUserUseCase = RegisterUserUseCase(get(), get()),
            loginUserUseCase = LoginUserUseCase(get(), get()),
            getAllUsers = GetAllUsers(get(), get())
        )
    }

    single<DefaultDispatchers> {
        DefaultDispatchersImpl()
    }

}