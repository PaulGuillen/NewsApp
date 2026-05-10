package com.devpaul.profile.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class IncrementLikeUC(
    private val profileRepository: ProfileRepository
) {

    suspend fun incrementLike(
        type: String,
        commentId: String,
        userId: String,
        increment: Boolean
    ): Output<GenericEntity, Throwable> {
        return try {
            Output.Success(
                profileRepository.incrementLike(
                    type = type,
                    commentId = commentId,
                    userId = userId,
                    increment = increment,
                )
            )
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}