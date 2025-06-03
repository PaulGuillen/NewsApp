package com.devpaul.news.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.news.domain.entity.CountryEntity
import org.koin.core.annotation.Factory
import com.devpaul.core_domain.entity.transform
import com.devpaul.news.data.datasource.mapper.toDomain
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity

@Factory
class NewsServiceDS(
    private val newsService: NewsService,
) {
    suspend fun countryService(): DefaultOutput<CountryEntity> {
        return safeApiCall {
            newsService.country()
        }.transform { it.toDomain() }
    }

    suspend fun googleService() : DefaultOutput<GoogleEntity> {
        return safeApiCall {
            newsService.google()
        }.transform { it.toDomain() }
    }

    suspend fun deltaProjectService() : DefaultOutput<DeltaProjectEntity> {
        return safeApiCall {
            newsService.deltaProject()
        }.transform { it.toDomain() }
    }

    suspend fun redditService(): DefaultOutput<RedditEntity> {
        return safeApiCall {
            newsService.reddit()
        }.transform { it.toDomain()  }
    }

}