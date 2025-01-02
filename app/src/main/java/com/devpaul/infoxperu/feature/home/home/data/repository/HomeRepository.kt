package com.devpaul.infoxperu.feature.home.home.data.repository

import com.devpaul.infoxperu.core.urls.ApiService
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.UITResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun dollarQuote(): DollarQuoteResponse {
        val response = apiService.dollarQuote()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception(ERROR_FETCHING_DATA)
        } else {
            val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
            throw ApiException(response.code(), errorMessage)
        }
    }

    suspend fun uit(): UITResponse {
        val response = apiService.dataUIT()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception(ERROR_FETCHING_DATA)
        } else {
            val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
            throw ApiException(response.code(), errorMessage)
        }
    }

    companion object {
        const val ERROR_FETCHING_DATA = "Error fetching data: response body is null"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}