package com.devpaul.infoxperu.feature.util.ui.news_screen

import android.content.Context
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
import com.devpaul.infoxperu.core.extension.formatPublishedAt
import com.devpaul.infoxperu.domain.models.res.ArticleNewsResponse
import com.devpaul.infoxperu.domain.models.res.SourceResponse
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun NewsSingleCard(articleNewsResponse: ArticleNewsResponse, context: Context) {
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
//            articleNewsResponse.link.let { url ->
//                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
//            }
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
                    text = articleNewsResponse.source.name,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Right
                )
            }
            Text(
                text = articleNewsResponse.title,
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
                text = formatPublishedAt(articleNewsResponse.publishDate),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsAPICardPreview() {
    NewsSingleCard(
        ArticleNewsResponse(
            source = SourceResponse("1", "CNN"),
            author = "Paul",
            title = "Title",
            articleDescription = "",
            url = "https://www.google.com",
            imageUrl = "https://www.google.com",
            publishDate = "2021-09-01T00:00:00Z",
            content = "Content",
        ),
        LocalContext.current
    )
}
