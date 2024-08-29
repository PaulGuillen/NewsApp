package com.devpaul.infoxperu.domain.ui.news_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.core.extension.formatPubDate
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun GoogleNewsCard(googleNewsState: NewsItemJSON, context: Context) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(180.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(0.8.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = White,
        ),
        onClick = {
            googleNewsState.link.let { url ->
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = googleNewsState.source.name,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Right
                )
            }
            Text(
                text = googleNewsState.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = formatPubDate(googleNewsState.pubDate),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleNewsCardPreview() {
    GoogleNewsCard(
        NewsItemJSON(
            "Alerta roja desde mañana en 12 regiones del Perú: Senamhi pronostica fenómeno de gran magnitud - Infobae Perú",
            "peru",
            "Peru",
            "Wed, 28 Aug 2024 15:02:00 GMT",
            "Peru",
            NewsSourceJSON("Infobae Perú", "https://www.infobae.com")
        ),
        context = LocalContext.current
    )
}
