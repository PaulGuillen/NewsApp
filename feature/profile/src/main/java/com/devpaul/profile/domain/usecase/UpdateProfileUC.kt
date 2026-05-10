package com.devpaul.profile.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class UpdateProfileUC(
    private val profileRepository: ProfileRepository
) {

    suspend fun updateProfile(
        uid: String,
        profileUser: UpdateRequest
    ): Output<GenericEntity, Throwable> {
        return try {
            Output.Success(profileRepository.updateUserData(uid, profileUser))
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}