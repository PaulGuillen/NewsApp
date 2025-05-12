package com.devpaul.navigation.core.jetpack

interface AppNavigator {
    fun navigateTo(screen: Screen)
    fun popBack()
}