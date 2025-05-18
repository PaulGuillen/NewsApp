package com.devpaul.home.domain.repository

import com.devpaul.core_data.model.DollarQuoteResponse
import com.devpaul.core_data.model.GDELProject
import com.devpaul.core_data.model.GoogleNewsXML
import com.devpaul.core_data.model.NewsResponse
import com.devpaul.core_data.model.RedditResponse
import com.devpaul.core_data.model.UITResponse

interface HomeRepository {

    suspend fun dollarQuote(): DollarQuoteResponse

    suspend fun dataUIT(): UITResponse

    suspend fun googleNews(query: String, language: String): GoogleNewsXML

    suspend fun deltaProject(query: String, mode: String, format: String): GDELProject

    suspend fun redditNews(country: String): RedditResponse

    suspend fun newsAPI(initLetters: String): NewsResponse

}