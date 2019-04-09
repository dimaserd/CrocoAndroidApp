package com.example.crocoandroidapp.service_locator

import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.SchedulersProviderImpl
import org.koin.dsl.module

val utilsModule = module {
    single<SchedulersProvider> { SchedulersProviderImpl() }
}
