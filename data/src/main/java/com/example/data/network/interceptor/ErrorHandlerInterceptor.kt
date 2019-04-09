package com.example.data.network.interceptor

import com.example.data.converter.ServerErrorConverter
import okhttp3.Interceptor
import okhttp3.Response

class ErrorHandlerInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val code = response.code()

        when (code) {
            in 400..599 -> throw ServerErrorConverter.fromNetwork(response)
        }

        return response
    }
}
