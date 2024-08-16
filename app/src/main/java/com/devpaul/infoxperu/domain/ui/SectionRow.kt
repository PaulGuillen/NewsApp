package com.devpaul.infoxperu.domain.ui

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.domain.models.res.SectionItem

@Composable
fun SectionsRow(sections: List<SectionItem>, context: Context) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 10.dp)
    ) {
        sections.forEach { sectionItem ->
            ItemSection(sectionItem, context)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionsRowPreview() {
    SectionsRow(
        sections = listOf(
            SectionItem("Noticias", "news"),
            SectionItem("Distritos", "districts")
        ),
        context = LocalContext.current
    )
}
