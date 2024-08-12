package com.devpaul.infoxperu.core.modules

import BaseUrlInterceptor
import com.devpaul.infoxperu.BuildConfig
import com.devpaul.infoxperu.core.urls.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BaseUrlPeru")
    fun provideBaseUrlPeru(): HttpUrl = BuildConfig.BASE_URL_PERU.toHttpUrlOrNull()!!

    @Provides
    @Singleton
    @Named("BaseUrlNews")
    fun provideBaseUrlNews(): HttpUrl = BuildConfig.BASE_URL_NEWS.toHttpUrlOrNull()!!

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("BaseUrlPeru") baseUrlPeru: HttpUrl,
        @Named("BaseUrlNews") baseUrlNews: HttpUrl
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BaseUrlInterceptor(baseUrlPeru, baseUrlNews))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL_PERU) // La base URL por defecto
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
