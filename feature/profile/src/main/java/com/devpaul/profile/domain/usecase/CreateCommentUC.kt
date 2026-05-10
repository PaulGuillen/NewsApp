package com.devpaul.profile.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class CreateCommentUC(
    private val profileRepository: ProfileRepository
) {

    suspend fun createComment(request: CommentRequest): Output<CommentEntity, Throwable> {
        return try {
            Output.Success(profileRepository.createComment(request))
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}