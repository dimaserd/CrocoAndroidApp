package com.example.crocoandroidapp

import android.app.Application
import com.example.crocoandroidapp.service_locator.loginModule
import com.example.crocoandroidapp.service_locator.networkModule
import com.example.crocoandroidapp.service_locator.persistentModule
import com.example.crocoandroidapp.service_locator.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CrocoAndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CrocoAndroidApp)
            modules(loginModule, networkModule, persistentModule, utilsModule)
        }
    }
}
