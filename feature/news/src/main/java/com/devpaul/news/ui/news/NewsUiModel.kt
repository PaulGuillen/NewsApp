package com.devpaul.news.ui.news

import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity

data class NewsUiModel(
    val country: CountryEntity? = null,
    val countryError: String? = null,
    val google: GoogleEntity? = null,
    val googleError: String? = null,
    val deltaProject: DeltaProjectEntity? = null,
    val deltaProjectError: String? = null,
    val reddit: RedditEntity ? = null,
    val redditError: String? = null
)