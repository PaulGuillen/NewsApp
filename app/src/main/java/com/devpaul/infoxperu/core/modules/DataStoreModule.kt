package com.devpaul.infoxperu.core.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.devpaul.infoxperu.core.manager.DataStoreUseCaseImpl
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import androidx.datastore.preferences.preferencesDataStore

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    fun provideDataStoreUseCase(dataStore: DataStore<Preferences>): DataStoreUseCase {
        return DataStoreUseCaseImpl(dataStore)
    }
}
