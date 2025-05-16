package com.devpaul.navigation.core.jetpack

import com.devpaul.core_domain.Screen

interface AppNavigator {
    fun navigateTo(screen: Screen, popUpTo: Screen? = null, inclusive: Boolean = false)
    fun popBack()
}