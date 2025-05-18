package com.devpaul.home.domain.usecase

import com.devpaul.core_data.model.UITResponse
import com.devpaul.core_data.viewmodel.CoroutineDispatcherProvider
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.repository.HomeRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class UITUseCase(
    private val repository: HomeRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) {
    suspend operator fun invoke(): ResultState<UITResponse> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = repository.dataUIT()
                ResultState.Success(response)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }
}