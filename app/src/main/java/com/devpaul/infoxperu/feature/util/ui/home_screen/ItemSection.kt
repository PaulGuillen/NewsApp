package com.devpaul.infoxperu.feature.util.ui.home_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun ItemSection(sectionItem: SectionItem, context: Context) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(64.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(0.8.dp, Color.Black),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Black
        ),
        onClick = {
            Toast.makeText(context, sectionItem.type, Toast.LENGTH_SHORT).show()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = sectionItem.title,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
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
