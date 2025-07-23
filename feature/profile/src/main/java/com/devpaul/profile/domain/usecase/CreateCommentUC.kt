package com.devpaul.profile.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class CreateCommentUC(
    private val profileRepository: ProfileRepository
) : SimpleUC.ParamsAndResult<CreateCommentUC.Params, DefaultOutput<CreateCommentUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return profileRepository.createPost(params.createPost).transformHttpError {
            Failure.CreateCommentError(it)
        }.transform {
            Success.CreateCommentSuccess(it)
        }
    }

    data class Params(val createPost: CommentRequest)

    sealed class Failure : Defaults.CustomError() {
        data class CreateCommentError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class CreateCommentSuccess(val comment: CommentEntity) : Success()
    }
}