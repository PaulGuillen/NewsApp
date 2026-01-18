package com.devpaul.news.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.news.data.datasource.mapper.toDomain
import com.devpaul.news.data.datasource.remote.NewsServiceDS
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.DeltaProjectDataEntity
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
    ): DefaultOutput<GoogleEntity> {
        return serviceDS.googleService(
            q = q,
            hl = hl,
        )
    }

    override suspend fun deltaProjectService(
        q: String,
        mode: String,
        format: String,
    ): DeltaProjectDataEntity {
        return serviceDS.deltaProjectService(
            query = q,
            mode = mode,
            format = format,
        ).toDomain()
    }

    override suspend fun redditService(country: String): RedditEntity {
        return serviceDS.redditService(country = country).toDomain()
    }
}