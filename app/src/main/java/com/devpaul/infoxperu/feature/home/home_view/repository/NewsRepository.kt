package com.devpaul.infoxperu.feature.home.home_view.repository

import com.devpaul.infoxperu.core.urls.ApiService
import com.devpaul.infoxperu.core.urls.ApiServiceGoogleNews
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsXML
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import timber.log.Timber
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
                    ?: throw Exception("Error fetching data: response body is null")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            Timber.e("Error fetching data: ${e.message}")
            throw e
        }
    }

    suspend fun deltaProject(query: String, mode: String, format: String): GDELProject {
        try {
            val response = apiService.deltaProject(query, mode, format).execute()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception("Error fetching data: response body is null")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            Timber.e("Error fetching data: ${e.message}")
            throw e
        }
    }

    suspend fun redditNews(country: String): RedditResponse {
        try {
            val response = apiService.redditNews(country).execute()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception("Error fetching data: response body is null")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Timber.e("Error fetching data: $errorMessage")
                throw ApiException(response.code(), errorMessage)

            }
        } catch (e: Exception) {
            Timber.e("Error fetching data: ${e.message}")
            throw e
        }
    }

    fun newsAPI(initLetters: String): NewsResponse {
        try {
            val response = apiService.newsAPI(initLetters).execute()
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception("Error fetching data: response body is null")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Timber.e("Error fetching data: $errorMessage")
                throw ApiException(response.code(), errorMessage)

            }
        } catch (e: Exception) {
            Timber.e("Error fetching data: ${e.message}")
            throw e
        }
    }

}
