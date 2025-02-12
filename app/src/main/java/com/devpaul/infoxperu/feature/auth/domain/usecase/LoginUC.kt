package com.devpaul.infoxperu.feature.auth.domain.usecase

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.auth.domain.entity.LoginE
import com.devpaul.infoxperu.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUC @Inject constructor(
    private val authRepository: AuthRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(params: Params): ResultState<LoginE> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = authRepository.login(params.requestLogin)
                ResultState.Success(response)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    data class Params(val requestLogin: RequestLogin)
}