package com.example.crocoandroidapp.service_locator

import com.example.data.network.client.OkHttpClientFactory
import com.example.data.network.interceptor.ErrorHandlerInterceptor
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    factory { GsonBuilder().setLenient().create() }
    factory<ClearableCookieJar> { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(androidContext())) }
    factory { ErrorHandlerInterceptor() }
    factory { OkHttpClientFactory(get(), get()).createClient() }
}
