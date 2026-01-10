package com.devpaul.news.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity

interface NewsRepository {

    suspend fun countryService(): CountryEntity

    suspend fun googleService(
        q: String,
        hl: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<GoogleEntity>

    suspend fun deltaProjectService(
        q: String,
        mode: String,
        format: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<DeltaProjectEntity>

    suspend fun redditService(
        country: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<RedditEntity>
}