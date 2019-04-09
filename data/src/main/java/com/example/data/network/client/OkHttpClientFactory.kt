package com.example.data.network.client

import com.example.data.network.interceptor.AddCookiesInterceptor
import com.example.data.network.interceptor.ErrorHandlerInterceptor
import com.example.data.network.interceptor.ReceivedCookieInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClientFactory(
    private val addCookiesInterceptor: AddCookiesInterceptor,
    private val receivedCookieInterceptor: ReceivedCookieInterceptor,
    private val errorHandlerInterceptor: ErrorHandlerInterceptor
) : OkHttpClient() {

    companion object {

        private const val CONNECT_TIMEOUT = 10L
    }

    fun createClient(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            addInterceptor(addCookiesInterceptor)
            addInterceptor(receivedCookieInterceptor)
            addInterceptor(errorHandlerInterceptor)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            build()
        }
    }
}