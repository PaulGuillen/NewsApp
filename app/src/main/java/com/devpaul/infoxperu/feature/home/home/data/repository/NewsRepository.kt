package com.devpaul.infoxperu.feature.home.home.data.repository

import com.devpaul.infoxperu.core.urls.ApiService
import com.devpaul.infoxperu.core.urls.ApiServiceGoogleNews
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsXML
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import javax.inject.Inject

open class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiServiceGoogleNews: ApiServiceGoogleNews
) {
    fun googleNews(query: String, language: String): GoogleNewsXML {
        try {
            val response = apiServiceGoogleNews.googleNews(query, language).execute()
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
            val response = apiService.deltaProject(query, mode, format).execute()
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
            val response = apiService.redditNews(country).execute()
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
            val response = apiService.newsAPI(initLetters).execute()
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
