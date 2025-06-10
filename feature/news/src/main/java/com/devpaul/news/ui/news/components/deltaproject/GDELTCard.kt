package com.devpaul.news.ui.news.components.deltaproject

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.White
import com.devpaul.news.domain.entity.DeltaProjectItemEntity

@Composable
fun GDELTCard(
    context: Context,
    deltaProject: DeltaProjectItemEntity
) {
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
            context.startActivity(Intent(Intent.ACTION_VIEW, deltaProject.url.toUri()))
        }
    ) {
        Column {
            val imagePainter = rememberAsyncImagePainter(
                model = deltaProject.socialImage,
                error = painterResource(id = R.drawable.baseline_newspaper_24)
            )

            val contentScale = when (imagePainter.state) {
                is AsyncImagePainter.State.Success -> ContentScale.Crop
                else -> ContentScale.Fit
            }

            Image(
                painter = imagePainter,
                contentDescription = "Image Article",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = contentScale
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = deltaProject.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = deltaProject.domain,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = deltaProject.seenDate,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}