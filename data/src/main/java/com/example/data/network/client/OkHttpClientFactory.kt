package com.example.data.network.client

import com.example.data.network.interceptor.ErrorHandlerInterceptor
import com.example.data.network.interceptor.SaveCookiesInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClientFactory(
    private val errorHandlerInterceptor: ErrorHandlerInterceptor,
    private val saveCookiesInterceptor: SaveCookiesInterceptor,
    private val cookieJar: ClearableCookieJar
) : OkHttpClient() {

    companion object {

        private const val CONNECT_TIMEOUT = 10L
    }

    fun createClient(): OkHttpClient {
        return with(Builder()) {
            addInterceptor(errorHandlerInterceptor)
            addInterceptor(saveCookiesInterceptor)
            cookieJar(cookieJar)
            addNetworkInterceptor(StethoInterceptor())
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            build()
        }
    }
}
