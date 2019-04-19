package com.example.crocoandroidapp.service_locator

import android.content.Context
import com.example.data.store.PersistentStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"

val persistentModule = module {
    single { androidContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
    single { PersistentStorage(get()) }
}
