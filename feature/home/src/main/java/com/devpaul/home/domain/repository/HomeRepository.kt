package com.devpaul.home.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.HolidayAlertEntity
import com.devpaul.home.domain.entity.SeasonEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.SunatExchangeRateEntity
import com.devpaul.home.domain.entity.UITEntity

interface HomeRepository {

    suspend fun dollarQuoteService(): DefaultOutput<DollarQuoteEntity>
    suspend fun uitService(): DefaultOutput<UITEntity>
    suspend fun seasonService(): DefaultOutput<SeasonEntity>
    suspend fun holidayAlertService(): DefaultOutput<HolidayAlertEntity>
    suspend fun sunatExchangeRateService(): DefaultOutput<SunatExchangeRateEntity>
    suspend fun sectionService(): SectionEntity
    suspend fun gratitudeService(): GratitudeEntity
}