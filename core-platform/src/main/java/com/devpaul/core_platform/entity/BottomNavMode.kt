package com.devpaul.core_platform.entity

sealed class BottomNavMode {
    data object Visible: BottomNavMode()
    data object Hide: BottomNavMode()
    data object None: BottomNavMode()
}