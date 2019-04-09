package com.example.crocoandroidapp.service_locator

import com.example.data.store.InMemoryStorage
import org.koin.dsl.module

val persistentModule = module {
    single { InMemoryStorage() }
}
