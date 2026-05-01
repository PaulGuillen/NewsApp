package com.devpaul.home.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.home.domain.entity.HolidayAlertEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class HolidayAlertUC(
    private val homeRepository: HomeRepository,
) : SimpleUC.OnlyResult<DefaultOutput<HolidayAlertUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return homeRepository.holidayAlertService()
            .transformHttpError {
                Failure.HolidayAlertError(it)
            }
            .transform {
                Success.HolidayAlertSuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class HolidayAlertError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class HolidayAlertSuccess(val holidayAlert: HolidayAlertEntity) : Success()
    }
}