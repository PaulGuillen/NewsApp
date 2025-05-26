package com.devpaul.home.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.home.domain.entity.UITEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class UITUseCase(
    private val homeRepository: HomeRepository,
) : SimpleUC.OnlyResult<DefaultOutput<UITUseCase.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return homeRepository.uit()
            .transformHttpError {
                Failure.UITError(it)
            }
            .transform {
                Success.UITSuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class UITError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class UITSuccess(val uit: UITEntity) : Success()
    }
}