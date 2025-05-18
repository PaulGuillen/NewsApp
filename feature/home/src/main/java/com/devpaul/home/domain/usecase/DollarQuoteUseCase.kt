package com.devpaul.home.domain.usecase

import com.devpaul.core_data.model.DollarQuoteResponse
import com.devpaul.core_data.viewmodel.CoroutineDispatcherProvider
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.repository.HomeRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class DollarQuoteUseCase(
    private val homeRepository: HomeRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) {
    suspend operator fun invoke(): ResultState<DollarQuoteResponse> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = homeRepository.dollarQuote()
                ResultState.Success(response)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }
}