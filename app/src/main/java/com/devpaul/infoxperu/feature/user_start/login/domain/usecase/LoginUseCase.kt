package com.devpaul.infoxperu.feature.user_start.login.domain.usecase

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.feature.user_start.login.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.user_start.login.domain.entity.LoginE
import com.devpaul.infoxperu.feature.user_start.login.domain.repository.LoginRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,  // Inyectamos la implementación de LoginRepository
    private val dispatcherProvider: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(params: Params): ResultState<LoginE> {
        return try {
            withContext(dispatcherProvider.io) {
                // Usamos el repositorio inyectado para realizar el login
                val response = loginRepository.login(params.requestLogin)
                ResultState.Success(response)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    data class Params(val requestLogin: RequestLogin)
}
