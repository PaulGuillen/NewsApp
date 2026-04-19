package com.devpaul.infoxperu

import com.devpaul.auth.AuthModule
import com.devpaul.core_data.CoreDataModule
import com.devpaul.core_data.manager.FirebaseModule
import com.devpaul.emergency.EmergencyModule
import com.devpaul.home.HomeModule
import com.devpaul.navigation.NavigationModule
import com.devpaul.news.NewsModule
import com.devpaul.profile.ProfileModule
import com.devpaul.shared.SharedModule
import com.example.mylist.MyListModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    [
        CoreDataModule::class,
        NavigationModule::class,
        FirebaseModule::class,
        SharedModule::class,
        AuthModule::class,
        HomeModule::class,
        NewsModule::class,
        EmergencyModule::class,
        MyListModule::class,
        ProfileModule::class,
    ]
)

@ComponentScan
class AppModule