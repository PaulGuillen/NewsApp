package com.devpaul.core_data

import android.content.Context
import com.devpaul.core_data.interceptor.ChuckerInterceptorProvider
import com.devpaul.core_data.interceptor.UserAgentInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@ComponentScan
class CoreDataModule {

    @Single
    fun okHttpClientProvider(
        context: Context,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        builder.addInterceptor(UserAgentInterceptor())
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        if (BuildConfig.DEBUG) {
            ChuckerInterceptorProvider.provide(context)?.let {
                builder.addInterceptor(it)
            }
        }

        return builder.build()
    }

    @Single
    fun gsonProvider(): GsonConverterFactory {
        val gson = GsonBuilder()
            .serializeNulls()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Single
    fun apiRetrofitProvider(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}
