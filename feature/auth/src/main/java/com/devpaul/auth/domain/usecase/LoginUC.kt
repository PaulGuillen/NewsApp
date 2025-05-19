package com.devpaul.auth.domain.usecase

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.domain.entity.Login
import com.devpaul.auth.domain.repository.AuthRepository
import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import org.koin.core.annotation.Factory

@Factory
class LoginUC(
    private val authRepository: AuthRepository,
) : SimpleUC.ParamsAndResult<LoginUC.Params, DefaultOutput<LoginUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return authRepository.login(params.loginRequest)
            .transformHttpError {
                Failure.LoginError(it)
            }
            .transform {
                Success.LoginSuccess(it)
            }
    }

    data class Params(val loginRequest: LoginRequest)

    sealed class Failure : Defaults.CustomError() {
        data class LoginError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class LoginSuccess(val login: Login) : Success()
    }
}