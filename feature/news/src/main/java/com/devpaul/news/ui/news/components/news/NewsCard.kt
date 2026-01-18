package com.devpaul.news.ui.news.components.news

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.devpaul.core_platform.theme.GreenDark
import com.devpaul.shared.ui.components.organisms.SourceChip

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    context: Context,
    title: String,
    url: String,
    date: String,
    author: String,
    source: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isSelected) GreenDark else MaterialTheme.colorScheme.outline,
        label = "card-border"
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, animatedBorderColor),
        onClick = {
            onSelect()
            context.startActivity(
                Intent(Intent.ACTION_VIEW, url.toUri())
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    SourceChip(text = source)
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = author,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    var selected by remember { mutableStateOf(false) }
    NewsCard(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp),
        context = LocalContext.current,
        title = "Desde 2024 que tienen un plan para la expansión hacia LATAM, creen que podría competir bien contra Tambo y Oxxo? O le irán mal como 7 Eleven en Indonesia?",
        url = "https://www.example.com",
        date = "01/01/2023",
        author = "Autor de la noticia",
        source = "Fuente",
        isSelected = selected,
        onSelect = { !selected }
    )
}