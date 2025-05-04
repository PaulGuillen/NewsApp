package com.devpaul.core_platform.activity

import com.devpaul.core_platform.entity.BottomNavMode
import com.devpaul.core_platform.entity.ToolbarMode

interface ActivityScaffold {

    fun setToolbarMode(toolbarMode: ToolbarMode) {}

    fun setBottomNavMode(bottomNavMode: BottomNavMode) {}
}