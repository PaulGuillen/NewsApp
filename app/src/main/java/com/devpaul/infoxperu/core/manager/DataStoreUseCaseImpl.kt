package com.devpaul.infoxperu.core.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class DataStoreUseCaseImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreUseCase {

    override suspend fun setValue(key: String?, value: String?) {
        if (key != null && value != null) {
            val dataStoreKey = stringPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[dataStoreKey] = value
            }
        }
    }

    override suspend fun setValue(key: String?, value: Int?) {
        if (key != null && value != null) {
            val dataStoreKey = intPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[dataStoreKey] = value
            }
        }
    }

    override suspend fun setValue(key: String?, value: Boolean?) {
        if (key != null && value != null) {
            val dataStoreKey = booleanPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[dataStoreKey] = value
            }
        }
    }

    override suspend fun setValue(key: String?, value: Float?) {
        if (key != null && value != null) {
            val dataStoreKey = floatPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[dataStoreKey] = value
            }
        }
    }

    override suspend fun setValue(key: String?, value: Long?) {
        if (key != null && value != null) {
            val dataStoreKey = longPreferencesKey(key)
            dataStore.edit { preferences ->
                preferences[dataStoreKey] = value
            }
        }
    }

    override suspend fun getString(key: String?): String? {
        return key?.let {
            val dataStoreKey = stringPreferencesKey(it)
            dataStore.data.map { preferences ->
                preferences[dataStoreKey] ?: ""
            }.first()
        }
    }

    override suspend fun getInt(key: String?): Int? {
        return key?.let {
            val dataStoreKey = intPreferencesKey(it)
            dataStore.data.map { preferences ->
                preferences[dataStoreKey] ?: 0
            }.first()
        }
    }

    override suspend fun getBoolean(key: String?): Boolean? {
        return key?.let {
            val dataStoreKey = booleanPreferencesKey(it)
            dataStore.data.map { preferences ->
                preferences[dataStoreKey] ?: false
            }.first()
        }
    }

    override suspend fun getFloat(key: String?): Float? {
        return key?.let {
            val dataStoreKey = floatPreferencesKey(it)
            dataStore.data.map { preferences ->
                preferences[dataStoreKey] ?: 0f
            }.first()
        }
    }

    override suspend fun getLong(key: String?): Long? {
        return key?.let {
            val dataStoreKey = longPreferencesKey(it)
            dataStore.data.map { preferences ->
                preferences[dataStoreKey] ?: 0L
            }.first()
        }
    }

    override suspend fun deleteAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun getAllPreference() {
        // DataStore no proporciona un método directo para obtener todas las preferencias.
        // Si necesitas esto, puedes iterar sobre todas las claves conocidas.
    }
}
