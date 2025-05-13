package com.devpaul.core_data.manager

import DataStoreSingleton
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devpaul.core_domain.use_case.DataStoreUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Single

@Single
class DataStoreUseCaseImpl(private val context: Context) : DataStoreUseCase {

    private val dataStore = DataStoreSingleton.getInstance(context)

    override fun setValue(key: String?, value: String?) {
        if (key != null && value != null) {
            val dataStoreKey = stringPreferencesKey(key)
            runBlocking {
                dataStore.edit { preferences ->
                    preferences[dataStoreKey] = value
                }
            }
        }
    }

    override fun setValue(key: String?, value: Int?) {
        if (key != null && value != null) {
            val dataStoreKey = intPreferencesKey(key)
            runBlocking {
                dataStore.edit { preferences ->
                    preferences[dataStoreKey] = value
                }
            }
        }
    }

    override fun setValue(key: String?, value: Boolean?) {
        if (key != null && value != null) {
            val dataStoreKey = booleanPreferencesKey(key)
            runBlocking {
                dataStore.edit { preferences ->
                    preferences[dataStoreKey] = value
                }
            }
        }
    }

    override fun setValue(key: String?, value: Float?) {
        if (key != null && value != null) {
            val dataStoreKey = floatPreferencesKey(key)
            runBlocking {
                dataStore.edit { preferences ->
                    preferences[dataStoreKey] = value
                }
            }
        }
    }

    override fun setValue(key: String?, value: Long?) {
        if (key != null && value != null) {
            val dataStoreKey = longPreferencesKey(key)
            runBlocking {
                dataStore.edit { preferences ->
                    preferences[dataStoreKey] = value
                }
            }
        }
    }

    override fun getString(key: String?): String? {
        return key?.let {
            val dataStoreKey = stringPreferencesKey(it)
            runBlocking {
                dataStore.data.first()[dataStoreKey] ?: ""
            }
        }
    }

    override fun getInt(key: String?): Int? {
        return key?.let {
            val dataStoreKey = intPreferencesKey(it)
            runBlocking {
                dataStore.data.first()[dataStoreKey] ?: 0
            }
        }
    }

    override fun getBoolean(key: String?): Boolean? {
        return key?.let {
            val dataStoreKey = booleanPreferencesKey(it)
            runBlocking {
                dataStore.data.first()[dataStoreKey] ?: false
            }
        }
    }

    override fun getFloat(key: String?): Float? {
        return key?.let {
            val dataStoreKey = floatPreferencesKey(it)
            runBlocking {
                dataStore.data.first()[dataStoreKey] ?: 0f
            }
        }
    }

    override fun getLong(key: String?): Long? {
        return key?.let {
            val dataStoreKey = longPreferencesKey(it)
            runBlocking {
                dataStore.data.first()[dataStoreKey] ?: 0L
            }
        }
    }

    override fun deleteAll() {
        runBlocking {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

    override fun remove(key: String?) {
        if (key != null) {
            val dataStoreKey = stringPreferencesKey(key)
            runBlocking {
                dataStore.edit { preferences ->
                    preferences.remove(dataStoreKey)
                }
            }
        }
    }

    override fun getAllPreference() {
        // DataStore no proporciona un método directo para obtener todas las preferencias.
        // Si necesitas esto, puedes iterar sobre todas las claves conocidas.
    }
}