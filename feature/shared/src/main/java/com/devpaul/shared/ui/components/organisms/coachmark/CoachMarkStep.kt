package com.devpaul.shared.ui.components.organisms.coachmark

data class CoachMarkStep(
    val id: String,
    val title: String,
    val description: String,
    val targetKey: String,
)

object CoachMarkTargets {
    const val COUNTRY_SELECTOR = "country_selector"
    const val SOURCE_SELECTOR = "source_selector"
}