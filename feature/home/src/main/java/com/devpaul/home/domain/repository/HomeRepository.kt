package com.devpaul.home.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.model.GDELProject
import com.devpaul.core_data.model.GoogleNewsXML
import com.devpaul.core_data.model.NewsResponse
import com.devpaul.core_data.model.RedditResponse
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity

interface HomeRepository {

    suspend fun dollarQuoteService(): DefaultOutput<DollarQuoteEntity>

    suspend fun uitService(): DefaultOutput<UITEntity>

    suspend fun sectionService(): DefaultOutput<SectionEntity>

    suspend fun gratitudeService(): DefaultOutput<GratitudeEntity>

    suspend fun googleNews(query: String, language: String): GoogleNewsXML

    suspend fun deltaProject(query: String, mode: String, format: String): GDELProject

    suspend fun redditNews(country: String): RedditResponse

    suspend fun newsAPI(initLetters: String): NewsResponse
}