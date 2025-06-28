package com.devpaul.profile.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory
class UserProfileUC(
    private val profileRepository: ProfileRepository,
) : SimpleUC.ParamsAndResult<UserProfileUC.Params, DefaultOutput<UserProfileUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return profileRepository.profileById(params.uid)
            .transformHttpError {
                Failure.ProfileError(it)
            }
            .transform {
                Success.ProfileSuccess(it)
            }
    }

    data class Params(val uid: String)

    sealed class Failure : Defaults.CustomError() {
        data class ProfileError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class ProfileSuccess(val profile: ProfileEntity) : Success()
    }
}