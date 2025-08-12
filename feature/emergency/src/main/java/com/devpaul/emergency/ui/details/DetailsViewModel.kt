package com.devpaul.emergency.ui.details

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class DetailsViewModel(

) : StatefulViewModel<DetailsUiState, DetailsUiIntent, DetailsUiEvent>(
    defaultUIState = {
        DetailsUiState()
    }
) {
    init {

    }

    override suspend fun onUiIntent(intent: DetailsUiIntent) {

    }

    private suspend fun fetchDetails() {

    }

    fun getTypeService(type: String?) {
        Timber.d("getTypeService: $type")
    }
}