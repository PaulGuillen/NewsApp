package com.devpaul.shared.domain

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.text.Normalizer

fun PaddingValues.only(
    top: Boolean = false,
    bottom: Boolean = false,
    start: Boolean = false,
    end: Boolean = false
): PaddingValues = PaddingValues(
    top = if (top) calculateTopPadding() else 0.dp,
    bottom = if (bottom) calculateBottomPadding() else 0.dp,
    start = if (start) calculateStartPadding(LayoutDirection.Ltr) else 0.dp,
    end = if (end) calculateEndPadding(LayoutDirection.Ltr) else 0.dp
)


fun String.normalizeSearch(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("\\p{Mn}+".toRegex(), "").lowercase().trim()
}