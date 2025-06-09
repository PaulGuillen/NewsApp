package com.devpaul.news.ui.components

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.devpaul.core_platform.extension.limitText
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.White
import com.devpaul.news.domain.entity.RedditPostItemEntity

@Composable
fun RedditCard(
    context: Context,
    redditPost: RedditPostItemEntity,
) {
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
            redditPost.url.let { url ->
                context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
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
                    text = limitText(redditPost.linkFlairText.toString(), 20),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = redditPost.title,
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
                text = limitText(redditPost.authorFullname, 10),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}