package com.devpaul.infoxperu.core.interceptor

import com.devpaul.infoxperu.BuildConfig
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url

        val newBaseUrl = when {
            originalUrl.encodedPath.contains("top-headline") -> BuildConfig.BASE_URL_NEWS
            originalUrl.encodedPath.contains("rss/search") -> BuildConfig.BASE_URL_GOOGLE_NEWS
            originalUrl.encodedPath.contains("v2/doc/doc") -> BuildConfig.BASE_URL_GDELT_PROJECT
            else -> BuildConfig.BASE_URL_PERU
        }.toHttpUrlOrNull()

        val newUrl = newBaseUrl?.newBuilder()
            ?.encodedPath(originalUrl.encodedPath)
            ?.query(originalUrl.query)
            ?.build() ?: originalUrl

        val newRequestBuilder = request.newBuilder().url(newUrl)

        if (newBaseUrl.toString().contains(BuildConfig.BASE_URL_PERU)) {
            newRequestBuilder.header("User-Agent", "devpaul")
        }

        if (originalUrl.encodedPath.contains("rss/search")) {
            newRequestBuilder.header("Accept-Encoding", "identity")
        }

        return chain.proceed(newRequestBuilder.build())
    }
}
