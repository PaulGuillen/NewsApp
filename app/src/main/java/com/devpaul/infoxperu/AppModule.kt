package com.devpaul.infoxperu

import com.devpaul.shared.SharedModule
import com.devpaul.auth.AuthModule
import com.devpaul.core_data.CoreDataModule
import com.devpaul.core_data.manager.FirebaseModule
import com.devpaul.home.HomeModule
import com.devpaul.navigation.NavigationModule
import com.devpaul.news.NewsModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module([
    CoreDataModule::class,
    NavigationModule::class,
    FirebaseModule::class,
    SharedModule::class,
    AuthModule::class,
    HomeModule::class,
    NewsModule::class,
])

@ComponentScan
class AppModule