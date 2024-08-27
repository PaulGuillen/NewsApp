package com.devpaul.infoxperu.domain.use_case

interface DataStoreUseCase {

    suspend fun setValue(key: String?, value: String?)
    suspend fun setValue(key: String?, value: Int?)
    suspend fun setValue(key: String?, value: Boolean?)
    suspend fun setValue(key: String?, value: Float?)
    suspend fun setValue(key: String?, value: Long?)

    suspend fun getString(key: String?): String?
    suspend fun getInt(key: String?): Int?
    suspend fun getBoolean(key: String?): Boolean?
    suspend fun getFloat(key: String?): Float?
    suspend fun getLong(key: String?): Long?

    suspend fun deleteAll()

    suspend fun getAllPreference()
}
