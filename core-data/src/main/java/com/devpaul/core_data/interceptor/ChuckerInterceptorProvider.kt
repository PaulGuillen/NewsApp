package com.devpaul.core_data.interceptor

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.devpaul.core_data.BuildConfig
import okhttp3.Interceptor

object ChuckerInterceptorProvider {

    fun provide(context: Context): Interceptor? {
        if (!BuildConfig.DEBUG) return null

        val collector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_DAY
        )

        return ChuckerInterceptor.Builder(context)
            .collector(collector)
            .maxContentLength(250_000L)
            .redactHeaders("Authorization", "Cookie", "X-Api-Key")
            .alwaysReadResponseBody(true)
            .build()
    }
}