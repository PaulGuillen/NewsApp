package com.devpaul.home.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.home.data.datasource.remote.HomeServiceDS
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory([HomeRepository::class])
class HomeRepositoryImpl(
    private val serviceDS: HomeServiceDS
) : HomeRepository {

    override suspend fun dollarQuoteService(): DefaultOutput<DollarQuoteEntity> {
        return serviceDS.dollarQuoteService()
    }

    override suspend fun uitService(): DefaultOutput<UITEntity> {
        return serviceDS.uitService()
    }

    override suspend fun sectionService(): SectionEntity {
        return serviceDS.sectionService()
    }

    override suspend fun gratitudeService(): GratitudeEntity {
        return serviceDS.gratitudeService()
    }
}