package com.devpaul.profile.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class IncrementLikeUC(
    private val profileRepository: ProfileRepository
) : SimpleUC.ParamsAndResult<IncrementLikeUC.Params, DefaultOutput<IncrementLikeUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return profileRepository.incrementLike(type = params.type, commentId = params.commentId)
            .transformHttpError {
                Failure.IncrementLikeError(it)
            }.transform {
            Success.IncrementLikeSuccess(it)
        }
    }

    data class Params(val type: String, val commentId: String)

    sealed class Failure : Defaults.CustomError() {
        data class IncrementLikeError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class IncrementLikeSuccess(val incrementLike: GenericEntity) : Success()
    }
}