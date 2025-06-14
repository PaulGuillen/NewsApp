package com.devpaul.home.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.home.data.datasource.mapper.toDomain
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity
import org.koin.core.annotation.Factory

@Factory
class HomeServiceDS (
    private val homeService: HomeService,
) {

    suspend fun dollarQuoteService(): DefaultOutput<DollarQuoteEntity> {
        return safeApiCall {
            homeService.dollarQuote()
        }.transform { it.toDomain() }
    }

    suspend fun uitService(): DefaultOutput<UITEntity> {
        return safeApiCall {
            homeService.uit()
        }.transform { it.toDomain() }
    }

    suspend fun sectionService(): DefaultOutput<SectionEntity> {
        return safeApiCall {
            homeService.sections()
        }.transform { it.toDomain() }
    }

    suspend fun gratitudeService(): DefaultOutput<GratitudeEntity> {
        return safeApiCall {
            homeService.gratitude()
        }.transform { it.toDomain() }
    }
}