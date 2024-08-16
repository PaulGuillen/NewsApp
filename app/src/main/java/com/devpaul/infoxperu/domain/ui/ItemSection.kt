package com.devpaul.infoxperu.domain.ui

import android.content.ClipData.Item
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.devpaul.infoxperu.domain.models.res.SectionItem

@Composable
fun ItemSection(sectionItem: SectionItem, context: Context) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(80.dp)
            .padding(8.dp)
            .clickable {
                Toast.makeText(context, sectionItem.type, Toast.LENGTH_SHORT).show()
            },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = sectionItem.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionItemPreview() {
    ItemSection(
        SectionItem("Noticias", "https://www.infoxperu.com/noticias"),
        context = LocalContext.current
    )
}
