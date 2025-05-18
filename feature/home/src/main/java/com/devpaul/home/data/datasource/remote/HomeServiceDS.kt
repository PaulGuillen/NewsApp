package com.devpaul.home.data.datasource.remote

import com.devpaul.core_data.model.ApiException
import com.devpaul.core_data.model.DollarQuoteResponse
import com.devpaul.core_data.model.GDELProject
import com.devpaul.core_data.model.GoogleNewsXML
import com.devpaul.core_data.model.NewsResponse
import com.devpaul.core_data.model.RedditResponse
import com.devpaul.core_data.model.UITResponse
import org.koin.core.annotation.Factory

@Factory
class HomeServiceDS (
    private val homeService: HomeService,
) {

    suspend fun dollarQuote(): DollarQuoteResponse {
        val response = homeService.dollarQuote()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception(ERROR_FETCHING_DATA)
        } else {
            val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
            throw ApiException(response.code(), errorMessage)
        }
    }

    suspend fun uit(): UITResponse {
        val response = homeService.dataUIT()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception(ERROR_FETCHING_DATA)
        } else {
            val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
            throw ApiException(response.code(), errorMessage)
        }
    }

    fun googleNews(query: String, language: String): GoogleNewsXML {
        try {
            val response = homeService.googleNews(query, language).execute()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception(ERROR_FETCHING_DATA)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun deltaProject(query: String, mode: String, format: String): GDELProject {
        try {
            val response = homeService.deltaProject(query, mode, format).execute()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception(ERROR_FETCHING_DATA)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun redditNews(country: String): RedditResponse {
        try {
            val response = homeService.redditNews(country).execute()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception(ERROR_FETCHING_DATA)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun newsAPI(initLetters: String): NewsResponse {
        try {
            val response = homeService.newsAPI(initLetters).execute()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception(ERROR_FETCHING_DATA)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        const val ERROR_FETCHING_DATA = "Error fetching data: response body is null"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}