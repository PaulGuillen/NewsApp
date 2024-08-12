package com.devpaul.infoxperu.feature.home.home_view.uc

import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.feature.home.home_view.repository.HomeRepository
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): DollarQuoteResponse {
        return repository.fetchData()
    }
}
