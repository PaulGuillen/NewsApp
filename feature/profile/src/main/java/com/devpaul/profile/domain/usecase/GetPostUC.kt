package com.devpaul.profile.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class GetPostUC(
    private val profileRepository: ProfileRepository
) : SimpleUC.OnlyResult<DefaultOutput<GetPostUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return profileRepository.getPost().transformHttpError {
            Failure.AllPostsError(it)
        }.transform {
            Success.AllPostsSuccess(it)
        }
    }

    sealed class Failure : Defaults.CustomError() {
        data class AllPostsError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class AllPostsSuccess(val allPosts: PostEntity) : Success()
    }
}