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
import com.devpaul.home.domain.entity.SectionDataEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.shared.ui.skeleton.SectionsRowSkeleton

@Composable
fun SectionsRow(
    context: Context,
    section: SectionEntity?,
    sectionError: String? = null,
    sectionLoading: Boolean,
) {
    if (sectionLoading) {
        SectionsRowSkeleton()
    } else {
        if (section != null) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                section.data.forEach { sectionItem ->
                    ItemSection(context = context, sectionItem = sectionItem)
                }
            }
        } else {
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
                            title = sectionError ?: "Error",
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
        section = SectionEntity(
            status = 200,
            message = "Success",
            data = SectionDataEntity(
                id = "1",
                title = "Section Title",
                type = "type"
            ).let { listOf(it, it, it) }
        ),
        sectionError = null,
        sectionLoading = false,
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowLoadingPreview() {
    SectionsRow(
        context = LocalContext.current,
        section = null,
        sectionError = null,
        sectionLoading = true,
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowErrorPreview() {
    SectionsRow(
        context = LocalContext.current,
        section = null,
        sectionError = "Error loading sections",
        sectionLoading = false,
    )
}