package com.devpaul.profile.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.UpdateEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class UpdateProfileUC(
    private val profileRepository: ProfileRepository
) : SimpleUC.ParamsAndResult<UpdateProfileUC.Params, DefaultOutput<UpdateProfileUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return profileRepository.updateUserData(params.uid, params.profileUser).transformHttpError {
            Failure.UpdateError(it)
        }.transform {
            Success.UpdateSuccess(it)
        }
    }

    data class Params(val uid: String, val profileUser: UpdateRequest)

    sealed class Failure : Defaults.CustomError() {
        data class UpdateError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class UpdateSuccess(val updateUser: UpdateEntity) : Success()
    }
}