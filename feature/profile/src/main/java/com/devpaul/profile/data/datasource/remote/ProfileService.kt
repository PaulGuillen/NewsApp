package com.devpaul.profile.data.datasource.remote

import org.koin.core.annotation.Factory
import retrofit2.Retrofit
import retrofit2.create

@Factory
class ProfileService(
    retrofit: Retrofit
): ProfileApi by retrofit.create()