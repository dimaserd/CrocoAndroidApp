package com.example.data.network.interceptor

import com.example.data.store.InMemoryStorage
import com.example.data.utils.CookieConstants.SET_COOKIE
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookieInterceptor(private val inMemoryStorage: InMemoryStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (originalResponse.headers(SET_COOKIE).isNotEmpty()) {
            val cookies = HashSet<String>()
            originalResponse.headers(SET_COOKIE).forEach { cookies.add(it) }
            inMemoryStorage.putAuthToken(cookies.first())
        }

        return originalResponse
    }
}
