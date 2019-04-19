package com.example.data.network.interceptor

import com.example.data.store.PersistentStorage
import com.example.data.utils.CookieConstants.SET_COOKIE
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookiesInterceptor(private val persistentStorage: PersistentStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (originalResponse.headers(SET_COOKIE).isNotEmpty()) {
            val cookies = HashSet<String>()
            originalResponse.headers(SET_COOKIE).forEach { header ->
                cookies.add(header)
            }
            persistentStorage.putCookies(cookies)
        }

        return originalResponse
    }
}
