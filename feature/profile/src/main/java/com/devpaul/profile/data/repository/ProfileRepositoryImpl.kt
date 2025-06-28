package com.devpaul.profile.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.profile.data.datasource.remote.ProfileServiceDS
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory([ProfileRepository::class])
class ProfileRepositoryImpl(
    private val profileServiceDS: ProfileServiceDS,
) : ProfileRepository {

    override suspend fun profileById(uid: String): DefaultOutput<ProfileEntity> {
        return profileServiceDS.getProfileById(uid = uid)
    }

}