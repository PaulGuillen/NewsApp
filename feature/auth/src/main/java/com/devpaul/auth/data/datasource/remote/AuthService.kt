package com.devpaul.auth.data.datasource.remote

import org.koin.core.annotation.Factory
import retrofit2.Retrofit
import retrofit2.create

@Factory
class AuthService(
    retrofit: Retrofit
): AuthApi by retrofit.create()