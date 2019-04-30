package com.example.crocoandroidapp

import android.app.Application
import com.example.crocoandroidapp.service_locator.*
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CrocoAndroidApp : Application() {

    companion object {

        const val APP_TAG = "Croco"
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@CrocoAndroidApp)
            modules(
                loginModule,
                profileModule,
                editProfileModule,
                taskModule,
                networkModule,
                choosingUsersModule,
                persistentModule,
                utilsModule
            )
        }
    }
}
