package com.devpaul.navigation.core.jetpack

import com.devpaul.core_data.Screen

interface AppNavigator {
    fun navigateTo(
        screen: Screen,
        popUpTo: Screen? = null,
        inclusive: Boolean = false,
        data: Map<String, Any?> = emptyMap()
    )

    fun popBack()
}