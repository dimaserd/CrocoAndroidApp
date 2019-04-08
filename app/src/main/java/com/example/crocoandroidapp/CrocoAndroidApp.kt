package com.example.crocoandroidapp

import android.app.Application
import org.koin.core.context.startKoin

class CrocoAndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules()
        }
    }
}
