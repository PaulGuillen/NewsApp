package com.devpaul.emergency.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.repository.EmergencyRepository
import org.koin.core.annotation.Factory

@Factory
class LimaSecurityUC(
    private val emergencyRepository: EmergencyRepository
) : SimpleUC.ParamsAndResult<LimaSecurityUC.Params, DefaultOutput<LimaSecurityUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return emergencyRepository.limaSecurityService(params.page, params.perPage)
            .transformHttpError {
                Failure.LimaSecurityError(it)
            }.transform {
            Success.LimaSecuritySuccess(it)
        }
    }

    data class Params(val page: Int, val perPage: Int)

    sealed class Failure : Defaults.CustomError() {
        data class LimaSecurityError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class LimaSecuritySuccess(val general: GeneralEntity) : Success()
    }
}