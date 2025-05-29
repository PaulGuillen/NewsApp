package com.devpaul.home.ui.home

import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity

data class HomeUiModel(
    val dollarQuote: DollarQuoteEntity? = null,
    val uit: UITEntity? = null,
    val gratitude: GratitudeEntity? = null,
    val section: SectionEntity? = null,
    val dollarQuoteError: String? = null,
    val uitError: String? = null,
    val gratitudeError: String? = null,
    val sectionError: String? = null
)