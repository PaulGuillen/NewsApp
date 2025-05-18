package com.devpaul.home.data.datasource.remote

import org.koin.core.annotation.Factory
import retrofit2.Retrofit
import retrofit2.create

@Factory
class HomeService(
    retrofit: Retrofit
): HomeApi by retrofit.create()