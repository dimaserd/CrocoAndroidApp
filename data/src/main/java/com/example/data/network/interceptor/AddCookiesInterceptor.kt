package com.example.data.network.interceptor

import com.example.data.store.InMemoryStorage
import com.example.data.utils.CookieConstants.COOKIE
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor(private val inMemoryStorage: InMemoryStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val authToken = inMemoryStorage.getAuthToken()
        authToken.let { builder.addHeader(COOKIE, it) }

        return chain.proceed(builder.build())
    }
}
