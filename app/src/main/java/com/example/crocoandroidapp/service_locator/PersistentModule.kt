package com.example.crocoandroidapp.di

import com.example.data.store.InMemoryStorage
import org.koin.dsl.module

val persistentModule = module {
    single { InMemoryStorage() }
}