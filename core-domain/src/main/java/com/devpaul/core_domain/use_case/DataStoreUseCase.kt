package com.devpaul.core_domain.use_case

interface DataStoreUseCase {

    fun setValue(key: String?, value: String?) {}
    fun setValue(key: String?, value: Int?) {}
    fun setValue(key: String?, value: Boolean?) {}
    fun setValue(key: String?, value: Float?) {}
    fun setValue(key: String?, value: Long?) {}

    fun getString(key: String?): String? = null
    fun getInt(key: String?): Int? = null
    fun getBoolean(key: String?): Boolean? = null
    fun getFloat(key: String?): Float? = null
    fun getLong(key: String?): Long? = null

    fun deleteAll() {}
    fun remove(key: String?) {}
    fun getAllPreference() {}
}
