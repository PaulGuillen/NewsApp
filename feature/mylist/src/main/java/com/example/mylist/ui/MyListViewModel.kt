package com.example.mylist.ui

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MyListViewModel : StatefulViewModel<MyListUiState, MyListUiIntent, MyListUiEvent>(
    defaultUIState = {
        MyListUiState()
    }
) {

    override suspend fun onUiIntent(intent: MyListUiIntent) {
        TODO("Not yet implemented")
    }
}