package com.devpaul.core_data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("User-Agent", "devpaul")
            .build()

        return chain.proceed(request)
    }
}