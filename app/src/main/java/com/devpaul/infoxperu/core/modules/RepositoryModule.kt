package com.devpaul.infoxperu.core.modules

import com.devpaul.infoxperu.feature.user_start.login.data.datasource.ds.LoginServiceDS
import com.devpaul.infoxperu.feature.user_start.login.data.repository.LoginRepositoryImpl
import com.devpaul.infoxperu.feature.user_start.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Esto lo hace disponible a nivel de toda la aplicación
abstract class RepositoryModule {

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

}
