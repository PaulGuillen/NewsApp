package com.devpaul.home.ui.components

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.entity.SectionDataEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.shared.ui.skeleton.SectionsRowSkeleton

@Composable
fun SectionsRow(
    context: Context,
    sectionState: ResultState<SectionEntity>,
) {
    when (sectionState) {
        is ResultState.Loading -> {
            SectionsRowSkeleton()
        }

        is ResultState.Success -> {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                sectionState.response.data.forEach { sectionItem ->
                    ItemSection(context = context, sectionItem = sectionItem)
                }
            }
        }

        is ResultState.Error -> {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                repeat(3) {
                    ItemSection(
                        context = context,
                        sectionItem = SectionDataEntity(
                            id = "0",
                            title = sectionState.message,
                            type = "error"
                        )
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SectionsRowSuccessPreview() {
    SectionsRow(
        context = LocalContext.current,
        sectionState = ResultState.Success(
            SectionEntity(
                status = 200,
                message = "Success",
                data = SectionDataEntity(
                    id = "1",
                    title = "Section Title",
                    type = "type"
                ).let { listOf(it, it, it) }
            ),
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowLoadingPreview() {
    SectionsRow(
        context = LocalContext.current,
        sectionState = ResultState.Loading,
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowErrorPreview() {
    SectionsRow(
        context = LocalContext.current,
        sectionState = ResultState.Error(message = "Error loading sections"),
    )
}