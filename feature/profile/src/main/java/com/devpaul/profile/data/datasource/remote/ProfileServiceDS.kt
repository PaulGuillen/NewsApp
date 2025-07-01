package com.devpaul.profile.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.mapper.toDomain
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.UpdateEntity
import org.koin.core.annotation.Factory

@Factory
class ProfileServiceDS(
    private val profileServiceDS: ProfileService
) {
    suspend fun getProfileById(uid: String): DefaultOutput<ProfileEntity> {
        return safeApiCall {
            profileServiceDS.getProfileById(uid)
        }.transform { it.toDomain() }
    }

    suspend fun getUpdateUserData(
        uid: String,
        profileUser: UpdateRequest,
    ): DefaultOutput<UpdateEntity> {
        return safeApiCall {
            profileServiceDS.updateUserData(uid, profileUser)
        }.transform { it.toDomain() }
    }
}