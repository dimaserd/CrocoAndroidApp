package com.example.crocoandroidapp.service_locator

import com.example.data.network.client.OkHttpClientFactory
import com.example.data.network.interceptor.AddCookiesInterceptor
import com.example.data.network.interceptor.ErrorHandlerInterceptor
import com.example.data.network.interceptor.ReceivedCookieInterceptor
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val networkModule = module {
    factory { GsonBuilder().setLenient().create() }
    factory { AddCookiesInterceptor(get()) }
    factory { ReceivedCookieInterceptor(get()) }
    factory { ErrorHandlerInterceptor() }
    factory { OkHttpClientFactory(get(), get(), get()).createClient() }
}
