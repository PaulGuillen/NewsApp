package com.devpaul.infoxperu.feature.auth.domain.usecase

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.util.getErrorMessageFromThrowable
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRecoveryPassword
import com.devpaul.infoxperu.feature.auth.domain.entity.RecoveryPasswordE
import com.devpaul.infoxperu.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecoveryPasswordUC @Inject constructor(
    private val authRepository: AuthRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(params: Params): ResultState<RecoveryPasswordE> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = authRepository.recoveryPassword(params.requestLogin)
                ResultState.Success(response)
            }
        } catch (e: Exception) {
            val errorMessage = getErrorMessageFromThrowable(e)
            throw Throwable(errorMessage)
        }
    }

    data class Params(val requestLogin: RequestRecoveryPassword)
}