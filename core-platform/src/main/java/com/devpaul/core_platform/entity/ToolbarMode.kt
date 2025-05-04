package com.devpaul.core_platform.entity

sealed class ToolbarMode {

    data class Visible(
        val title: CharSequence ? = null,
        val allowBack: Boolean = false
    ): ToolbarMode()

    data object Default: ToolbarMode()

    data object Hide: ToolbarMode()

    data object None: ToolbarMode()
}