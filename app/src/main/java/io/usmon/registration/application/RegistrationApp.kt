package io.usmon.registration.application

import android.app.Application
import io.usmon.registration.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

// Created by Usmon Abdurakhmanv on 6/9/2022.

class RegistrationApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}