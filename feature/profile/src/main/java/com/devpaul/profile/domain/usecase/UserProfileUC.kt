package com.devpaul.profile.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class UserProfileUC(
    private val profileRepository: ProfileRepository,
) {

    suspend fun profileById(uid: String): Output<ProfileEntity, Throwable> {
        return try {
            Output.Success(profileRepository.profileById(uid))
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}