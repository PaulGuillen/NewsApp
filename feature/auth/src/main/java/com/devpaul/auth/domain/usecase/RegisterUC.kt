package com.devpaul.auth.domain.usecase

import com.devpaul.auth.data.datasource.dto.register.RegisterRequest
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.domain.repository.AuthRepository
import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import org.koin.core.annotation.Factory

@Factory
class RegisterUC(
    private val authRepository: AuthRepository,
) : SimpleUC.ParamsAndResult<RegisterUC.Params, DefaultOutput<RegisterUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return authRepository.register(params.registerRequest)
            .transformHttpError {
                Failure.RegisterError(it)
            }
            .transform {
                Success.RegisterSuccess(it)
            }
    }

    data class Params(val registerRequest: RegisterRequest)

    sealed class Failure : Defaults.CustomError() {
        data class RegisterError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class RegisterSuccess(val register: Register) : Success()
    }
}