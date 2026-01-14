package com.devpaul.news.data.datasource.remote

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import retrofit2.Retrofit
import retrofit2.create

@Factory
class GoogleService(
    @Named("xmlRetrofit") retrofit: Retrofit
) : GoogleApi by retrofit.create()
