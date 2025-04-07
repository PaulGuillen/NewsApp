package com.devpaul.infoxperu.feature.util.ui.news_screen

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
import com.devpaul.infoxperu.core.extension.limitText
import com.devpaul.infoxperu.domain.models.res.PostDataWrapper
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun RedditCard(redditChildren: PostDataWrapper, context: Context) {
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
            redditChildren.data.url.let { url ->
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
                    text = limitText(redditChildren.data.linkFlairText.toString(), 20),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = redditChildren.data.title.toString(),
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
                text = limitText(redditChildren.data.authorFullname.toString(), 10),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RedditCardPreview() {
    RedditCard(PostDataWrapper(), context = LocalContext.current)
}
