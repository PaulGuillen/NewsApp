package com.devpaul.infoxperu.domain.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.R

@Composable
fun SectionsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SectionItem(iconRes = R.drawable.ic_launcher_background, title = "Noticias")
        SectionItem(iconRes = R.drawable.ic_launcher_background, title = "Servicios")
        SectionItem(iconRes = R.drawable.ic_launcher_background, title = "Distritos")
    }
}

@Preview(showBackground = true)
@Composable
fun SectionsRowPreview() {
    SectionsRow()
}