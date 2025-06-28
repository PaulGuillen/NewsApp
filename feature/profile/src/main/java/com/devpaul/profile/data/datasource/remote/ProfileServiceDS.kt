package com.devpaul.profile.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.profile.domain.entity.ProfileEntity
import org.koin.core.annotation.Factory
import com.devpaul.profile.data.datasource.mapper.toDomain

@Factory
class ProfileServiceDS(
    private val profileServiceDS: ProfileService
) {
     suspend fun getProfileById(uid: String): DefaultOutput<ProfileEntity> {
         return safeApiCall {
             profileServiceDS.getProfileById(uid)
         }.transform { it.toDomain() }
     }
}