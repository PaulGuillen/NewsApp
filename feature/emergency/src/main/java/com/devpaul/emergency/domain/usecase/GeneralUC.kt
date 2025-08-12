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
class GeneralUC(
    private val emergencyRepository: EmergencyRepository
) : SimpleUC.ParamsAndResult<GeneralUC.Params, DefaultOutput<GeneralUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return emergencyRepository.generalService(params.page, params.perPage).transformHttpError {
            Failure.GeneralError(it)
        }.transform {
            Success.GeneralSuccess(it)
        }
    }

    data class Params(val page: Int, val perPage: Int)

    sealed class Failure : Defaults.CustomError() {
        data class GeneralError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class GeneralSuccess(val general: GeneralEntity) : Success()
    }
}