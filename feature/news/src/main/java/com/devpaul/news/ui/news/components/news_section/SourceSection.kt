package com.devpaul.news.ui.news.components.news_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.DeltaPrimary
import com.devpaul.core_platform.theme.DeltaSoft
import com.devpaul.core_platform.theme.GooglePrimary
import com.devpaul.core_platform.theme.GoogleSoft
import com.devpaul.core_platform.theme.RedditPrimary
import com.devpaul.core_platform.theme.RedditSoft
import com.devpaul.news.ui.news.NewsUiIntent
import com.devpaul.news.ui.news.NewsUiState
import com.devpaul.shared.ui.components.organisms.sourceselector.Source

@Composable
fun SourceSection(
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.Start // 🔥 A LA DERECHA
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            SourceFilterChip(
                text = "GOOGLE",
                isSelected = uiState.selectedSource == Source.GOOGLE,
                primary = GooglePrimary,
                soft = GoogleSoft,
                onClick = {
                    onIntent(NewsUiIntent.SelectSource(Source.GOOGLE))
                }
            )

            SourceFilterChip(
                text = "REDDIT",
                isSelected = uiState.selectedSource == Source.REDDIT,
                primary = RedditPrimary,
                soft = RedditSoft,
                onClick = {
                    onIntent(NewsUiIntent.SelectSource(Source.REDDIT))
                }
            )

            SourceFilterChip(
                text = "DELTA",
                isSelected = uiState.selectedSource == Source.DELTA,
                primary = DeltaPrimary,
                soft = DeltaSoft,
                onClick = {
                    onIntent(NewsUiIntent.SelectSource(Source.DELTA))
                }
            )
        }
    }
}