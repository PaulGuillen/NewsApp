package com.devpaul.infoxperu.core.modules

import com.devpaul.infoxperu.feature.auth.data.repository.AuthRepositoryImpl
import com.devpaul.infoxperu.feature.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // Esto lo hace disponible a nivel de toda la aplicación
abstract class RepositoryModule {

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

}
