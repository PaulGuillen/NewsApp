package com.devpaul.news.ui.news_detail

import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity

data class NewsDetailUiModel(
    val google: GoogleEntity? = null,
    val googleError: String? = null,
    val deltaProject: DeltaProjectEntity? = null,
    val deltaProjectError: String? = null,
    val reddit: RedditEntity? = null,
    val redditError: String? = null
)