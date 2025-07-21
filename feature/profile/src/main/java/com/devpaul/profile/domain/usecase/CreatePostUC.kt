package com.devpaul.profile.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.profile.data.datasource.dto.req.CreatePostRequest
import com.devpaul.profile.domain.entity.CreatePostEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class CreatePostUC(
    private val profileRepository: ProfileRepository
) : SimpleUC.ParamsAndResult<CreatePostUC.Params, DefaultOutput<CreatePostUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return profileRepository.createPost(params.createPost).transformHttpError {
            Failure.CreatePostError(it)
        }.transform {
            Success.CreatePostSuccess(it)
        }
    }

    data class Params(val createPost: CreatePostRequest)

    sealed class Failure : Defaults.CustomError() {
        data class CreatePostError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class CreatePostSuccess(val createPost: CreatePostEntity) : Success()
    }
}