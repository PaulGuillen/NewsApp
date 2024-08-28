package com.devpaul.infoxperu.core.modules

import com.devpaul.infoxperu.BuildConfig
import com.devpaul.infoxperu.core.interceptor.BaseUrlInterceptor
import com.devpaul.infoxperu.core.urls.ApiService
import com.devpaul.infoxperu.core.urls.ApiServiceGoogleNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
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
    @Named("BaseUrlGoogleNews")
    fun provideBaseUrlGoogleNews(): HttpUrl = BuildConfig.BASE_URL_GOOGLE_NEWS.toHttpUrlOrNull()!!

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("BaseUrlPeru") baseUrlPeru: HttpUrl,
        @Named("BaseUrlNews") baseUrlNews: HttpUrl,
        @Named("BaseUrlGoogleNews") baseUrlGoogleNews: HttpUrl
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BaseUrlInterceptor(baseUrlPeru, baseUrlNews, baseUrlGoogleNews))
            .build()
    }

    @Provides
    @Singleton
    @Named("RetrofitPeru")
    fun provideRetrofitPeru(
        okHttpClient: OkHttpClient,
        @Named("BaseUrlPeru") baseUrlPeru: HttpUrl
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrlPeru)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("RetrofitNews")
    fun provideRetrofitNews(
        okHttpClient: OkHttpClient,
        @Named("BaseUrlNews") baseUrlNews: HttpUrl
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrlNews)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("RetrofitGoogleNewsXML")
    fun provideRetrofitGoogleNewsXML(
        okHttpClient: OkHttpClient,
        @Named("BaseUrlGoogleNews") baseUrlGoogleNews: HttpUrl
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrlGoogleNews)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("RetrofitPeru") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGoogleNewsService(@Named("RetrofitGoogleNewsXML") retrofit: Retrofit): ApiServiceGoogleNews {
        return retrofit.create(ApiServiceGoogleNews::class.java)
    }
}

