package com.devpaul.core_data.interceptor

import android.content.Context
import com.devpaul.core_data.exception.InactiveNetworkException
import com.devpaul.core_data.util.connectivityManager
import com.devpaul.core_data.util.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Request

import okhttp3.Response
import org.koin.core.annotation.Single

@Single
class NetworkInterceptor(
    context: Context,
) : Interceptor {

    private val connectivityManager by context.connectivityManager()

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityManager.isNetworkAvailable()) {
            throw InactiveNetworkException()
        }
        val request: Request = chain.request()
        return chain.proceed(request)
    }
}