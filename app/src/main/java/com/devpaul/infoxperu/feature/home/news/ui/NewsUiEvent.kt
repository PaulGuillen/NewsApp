package com.devpaul.infoxperu.feature.home.news.ui

sealed class NewsUiEvent {

}


sealed class NewsUiIntent {
    data object DollarQuote : NewsUiIntent()
    data object UIT : NewsUiIntent()
    data object Gratitude : NewsUiIntent()
    data object Sections : NewsUiIntent()
}