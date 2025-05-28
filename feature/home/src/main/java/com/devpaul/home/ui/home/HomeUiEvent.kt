package com.devpaul.home.ui.home

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity

sealed class HomeUiEvent{
    data class DollarQuoteSuccess(val response: DollarQuoteEntity) : HomeUiEvent()
    data class DollarQuoteError(val error: Defaults.HttpError<String>) : HomeUiEvent()

    data class UITSuccess(val response: UITEntity) : HomeUiEvent()
    data class UITError(val error: Defaults.HttpError<String>) : HomeUiEvent()

    data class SectionSuccess(val response: SectionEntity) : HomeUiEvent()
    data class SectionError(val error: Defaults.HttpError<String>) : HomeUiEvent()

    data class GratitudeSuccess(val response: GratitudeEntity) : HomeUiEvent()
    data class GratitudeError(val error: Defaults.HttpError<String>) : HomeUiEvent()
}
