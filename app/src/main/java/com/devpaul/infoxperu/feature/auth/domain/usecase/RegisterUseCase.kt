package com.devpaul.infoxperu.feature.auth.domain.usecase

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRegister
import com.devpaul.infoxperu.feature.auth.domain.entity.RegisterE
import com.devpaul.infoxperu.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(params: Params): ResultState<RegisterE> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = authRepository.register(params.requestLogin)
                ResultState.Success(response)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    data class Params(val requestLogin: RequestRegister)
}