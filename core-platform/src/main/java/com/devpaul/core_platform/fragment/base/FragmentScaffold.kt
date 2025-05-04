package com.devpaul.core_platform.fragment.base

import com.devpaul.core_platform.entity.BottomNavMode
import com.devpaul.core_platform.entity.ToolbarMode

interface FragmentScaffold {

    fun toolbarMode(): ToolbarMode = ToolbarMode.None

    fun bottomNavMode(): BottomNavMode = BottomNavMode.None
}