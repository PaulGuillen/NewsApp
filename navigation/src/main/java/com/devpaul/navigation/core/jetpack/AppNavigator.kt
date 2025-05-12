package com.devpaul.navigation.core.jetpack

import com.devpaul.core_domain.Screen

interface AppNavigator {
    fun navigateTo(screen: Screen)
    fun popBack()
}