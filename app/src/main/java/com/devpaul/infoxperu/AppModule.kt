package com.devpaul.infoxperu

import com.devpaul.auth.AuthModule
import com.devpaul.core_data.CoreDataModule
import com.devpaul.navigation.NavigationModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module([
    CoreDataModule::class,
    AuthModule::class,
])

@ComponentScan
class AppModule