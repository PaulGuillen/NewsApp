package com.devpaul.infoxperu.domain.ui.home_screen

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.domain.ui.skeleton.SectionsRowSkeleton

@Composable
fun SectionsRow(sectionItemsState: ResultState<List<SectionItem>>, context: Context) {
    SectionsRowContent(sectionItemsState = sectionItemsState, context = context)
}

@Composable
fun SectionsRowContent(sectionItemsState: ResultState<List<SectionItem>>, context: Context) {
    when (sectionItemsState) {
        is ResultState.Loading -> {
            SectionsRowSkeleton()
        }

        is ResultState.Success -> {
            if (sectionItemsState.data.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 10.dp)
                ) {
                    sectionItemsState.data.forEach { sectionItem ->
                        ItemSection(sectionItem, context)
                    }
                }
            } else {
                Text(text = "No hay secciones disponibles.")
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
                        SectionItem(
                            title = "Error",
                            type = "error"
                        ), context
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionsRowSuccessPreview() {
    val sectionsState = ResultState.Success(
        listOf(
            SectionItem("Noticias", "news"),
            SectionItem("Distritos", "districts")
        )
    )
    SectionsRowContent(sectionItemsState = sectionsState, context = LocalContext.current)
}

@Preview(showBackground = true)
@Composable
fun SectionsRowLoadingPreview() {
    SectionsRowContent(sectionItemsState = ResultState.Loading, context = LocalContext.current)
}

@Preview(showBackground = true)
@Composable
fun SectionsRowErrorPreview() {
    val sectionsState = ResultState.Error(Exception("Failed to load data"))
    SectionsRowContent(sectionItemsState = sectionsState, context = LocalContext.current)
}
