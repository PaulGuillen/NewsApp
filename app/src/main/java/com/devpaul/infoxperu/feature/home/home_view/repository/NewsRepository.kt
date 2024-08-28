package com.devpaul.infoxperu.feature.home.home_view.repository

import com.devpaul.infoxperu.core.urls.ApiServiceGoogleNews
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.GoogleNewsXML
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiServiceGoogleNews: ApiServiceGoogleNews
) {
    fun googleNews(query: String, language: String): GoogleNewsXML {
        val response = apiServiceGoogleNews.googleNews(query, language).execute()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Error fetching data: response body is null")
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            throw ApiException(response.code(), errorMessage)
        }
    }
}
