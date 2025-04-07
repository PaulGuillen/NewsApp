package com.devpaul.infoxperu.feature.util.ui.news_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.core.extension.formatIsoDate
import com.devpaul.infoxperu.domain.models.res.Article
import com.devpaul.infoxperu.feature.util.ui.utils.ArticleImage
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun GDELTCard(article: Article, context: Context) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(260.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Black
        ),
        onClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
        }
    ) {
        Column {
            ArticleImage(article = article)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.domain,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = formatIsoDate(article.seenDate),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

@Preview(showBackground = true, name = "GDELT Card Preview")
@Composable
fun PreviewGDELTCard() {
    GDELTCard(
        article = Article(
            "https://www.deperu.com/tv/wQfz0Keo-Os.venezuela-vs-canada-penales-resumen-y-goles-copa-america-2024-libero.UCk2OZrA0E6q6xp4bBKtf9KA.html",
            "",
            "Video : ? VENEZUELA VS CANADÁ PENALES , RESUMEN Y GOLES - COPA AMÉRICA 2024",
            "20240707T020000Z",
            "https://i.ytimg.com/vi/wQfz0Keo-Os/hqdefault.jpg",
            "deperu.com",
            "Galician",
            "United States",
        ), context = LocalContext.current
    )
}