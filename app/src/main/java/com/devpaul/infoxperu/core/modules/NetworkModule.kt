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
import retrofit2.Converter
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BaseUrlInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BaseUrl") baseUrl: HttpUrl,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    @Named("RetrofitReddit")
    fun provideReddit(okHttpClient: OkHttpClient): Retrofit {
        return provideRetrofit(
            okHttpClient,
            BuildConfig.BASE_URL_REDDIT.toHttpUrlOrNull()!!,
            GsonConverterFactory.create()
        )
    }

    @Provides
    @Singleton
    @Named("RetrofitPeru")
    fun provideRetrofitPeru(okHttpClient: OkHttpClient): Retrofit {
        return provideRetrofit(
            okHttpClient,
            BuildConfig.BASE_URL_PERU.toHttpUrlOrNull()!!,
            GsonConverterFactory.create()
        )
    }

    @Provides
    @Singleton
    @Named("RetrofitNews")
    fun provideRetrofitNews(okHttpClient: OkHttpClient): Retrofit {
        return provideRetrofit(
            okHttpClient,
            BuildConfig.BASE_URL_NEWS.toHttpUrlOrNull()!!,
            GsonConverterFactory.create()
        )
    }

    @Provides
    @Singleton
    @Named("RetrofitGoogleNewsXML")
    fun provideRetrofitGoogleNewsXML(okHttpClient: OkHttpClient): Retrofit {
        return provideRetrofit(
            okHttpClient,
            BuildConfig.BASE_URL_GOOGLE_NEWS.toHttpUrlOrNull()!!,
            SimpleXmlConverterFactory.create()
        )
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
