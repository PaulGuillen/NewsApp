package com.devpaul.home.ui.acknowledgments

sealed class AcknowledgmentUiIntent {
    data object GetGratitude : AcknowledgmentUiIntent()
}