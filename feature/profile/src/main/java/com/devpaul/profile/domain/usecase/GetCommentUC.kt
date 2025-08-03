package com.devpaul.profile.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class GetCommentUC(
    private val profileRepository: ProfileRepository
) : SimpleUC.ParamsAndResult<GetCommentUC.Params, DefaultOutput<GetCommentUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return profileRepository.getComments(params.limit, params.lastTimestamp)
            .transformHttpError {
                Failure.GetCommentError(it)
            }.transform {
                Success.GetCommentSuccess(it)
            }
    }

    data class Params(val limit: Int, val lastTimestamp: Long? = null)

    sealed class Failure : Defaults.CustomError() {
        data class GetCommentError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class GetCommentSuccess(val comment: GetCommentEntity) : Success()
    }
}