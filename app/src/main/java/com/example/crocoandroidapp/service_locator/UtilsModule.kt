package com.example.crocoandroidapp.di

import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.SchedulersProviderImpl
import org.koin.dsl.module

val utilsModule = module {
    single<SchedulersProvider> { SchedulersProviderImpl() }
}
