package com.devpaul.profile.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class GetCommentUC(
    private val profileRepository: ProfileRepository
) {

    suspend fun getComments(
        limit: Int,
        lastTimestamp: Long? = null
    ): Output<GetCommentEntity, Throwable> {
        return try {
            Output.Success(profileRepository.getComments(limit, lastTimestamp))
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}