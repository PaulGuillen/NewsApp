package com.devpaul.infoxperu.core.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class BaseUrlInterceptor @Inject constructor(
    @Named("BaseUrlPeru") private val baseUrlPeru: HttpUrl,
    @Named("BaseUrlNews") private val baseUrlNews: HttpUrl,
    @Named("BaseUrlGoogleNews") private val baseUrlGoogleNews: HttpUrl
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalUrl = request.url

        val newUrl = if (originalUrl.encodedPath.contains("v2")) {
            baseUrlNews.newBuilder()
                .encodedPath(originalUrl.encodedPath)
                .query(originalUrl.query)
                .build()
        } else if (originalUrl.encodedPath.contains("rss")) {
            baseUrlGoogleNews.newBuilder()
                .encodedPath(originalUrl.encodedPath)
                .query(originalUrl.query)
                .build()

        } else {
            baseUrlPeru.newBuilder()
                .encodedPath(originalUrl.encodedPath)
                .query(originalUrl.query)
                .build()
        }

        request = request.newBuilder()
            .url(newUrl)
            .header("Accept-Encoding", "identity")
            .header("User-Agent", "devpaul")
            .build()

        return chain.proceed(request)
    }
}
