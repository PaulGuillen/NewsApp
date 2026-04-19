package com.example.mylist.ui

sealed class MyListUiIntent {
    data object GetEmergencyServices : MyListUiIntent()
}