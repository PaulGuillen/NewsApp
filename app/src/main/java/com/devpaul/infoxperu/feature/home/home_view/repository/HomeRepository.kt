package com.devpaul.infoxperu.feature.home.home_view.repository

import com.devpaul.infoxperu.core.urls.ApiService
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun dollarQuote(): DollarQuoteResponse {
        val response = apiService.dollarQuote()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Error fetching data: response body is null")
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            throw ApiException(response.code(), errorMessage)
        }
    }
}
