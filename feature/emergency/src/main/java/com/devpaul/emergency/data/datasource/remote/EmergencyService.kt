package com.devpaul.emergency.data.datasource.remote

import org.koin.core.annotation.Factory
import retrofit2.Retrofit
import retrofit2.create

@Factory
class EmergencyService(
    retrofit: Retrofit
): EmergencyApi by retrofit.create()