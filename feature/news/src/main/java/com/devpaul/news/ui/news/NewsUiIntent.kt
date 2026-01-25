package com.devpaul.news.ui.news

import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.shared.ui.components.organisms.sourceselector.Source

sealed class NewsUiIntent {
    object GetCountries : NewsUiIntent()
    data class SelectCountry(val country: CountryItemEntity) : NewsUiIntent()
    data class SelectSource(val source: Source) : NewsUiIntent()
    object RetrySelectedSource : NewsUiIntent()
    object NextCoachMark : NewsUiIntent()
    object SkipCoachMark : NewsUiIntent()
    object CoachMarkCountryCompleted : NewsUiIntent()
    object CoachMarkSourceCompleted : NewsUiIntent()
}