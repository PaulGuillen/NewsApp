package com.devpaul.news.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.news.data.datasource.remote.NewsServiceDS
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory([NewsRepository::class])
class NewsRepositoryImpl(
    private val serviceDS: NewsServiceDS
) : NewsRepository {

    override suspend fun countryService(): CountryEntity {
        return serviceDS.countryService()
    }

    override suspend fun googleService(
        q: String,
        hl: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<GoogleEntity> {
        return serviceDS.googleService(
            q = q,
            hl = hl,
            page = page,
            perPage = perPage,
        )
    }

    override suspend fun deltaProjectService(
        q: String,
        mode: String,
        format: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<DeltaProjectEntity> {
        return serviceDS.deltaProjectService(
            q = q,
            mode = mode,
            format = format,
            page = page,
            perPage = perPage,
        )
    }

    override suspend fun redditService(
        country: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<RedditEntity> {
        return serviceDS.redditService(
            country = country,
            page = page,
            perPage = perPage,
        )
    }
}