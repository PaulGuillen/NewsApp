package com.example.mylist.ui

sealed class MyListUiEvent {
    data class NavigateToDetails(val type: String) : MyListUiEvent()
}