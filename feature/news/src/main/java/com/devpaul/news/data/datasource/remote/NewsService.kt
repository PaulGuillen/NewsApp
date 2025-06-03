package com.devpaul.news.data.datasource.remote

import org.koin.core.annotation.Factory
import retrofit2.Retrofit
import retrofit2.create

@Factory
class NewsService(
    retrofit: Retrofit
): NewsApi by retrofit.create()