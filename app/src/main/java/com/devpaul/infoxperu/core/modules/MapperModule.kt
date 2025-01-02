package com.devpaul.infoxperu.core.modules

import com.devpaul.infoxperu.feature.home.home.data.mapper.GoogleNewsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideGoogleNewsMapper(): GoogleNewsMapper {
        return GoogleNewsMapper()
    }
}