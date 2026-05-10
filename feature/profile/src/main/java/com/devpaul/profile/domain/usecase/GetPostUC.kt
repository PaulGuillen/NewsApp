package com.devpaul.profile.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class GetPostUC(
    private val profileRepository: ProfileRepository
) {

    suspend fun getPost(): Output<PostEntity, Throwable> {
        return try {
            Output.Success(profileRepository.getPost())
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}