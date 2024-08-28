package com.devpaul.infoxperu.domain.ui.news_screen

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun GoogleNewsCard(googleNewsState: NewsItemJSON, context: Context) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(140.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(0.8.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Black
        ),
        onClick = {

        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = googleNewsState.title,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleNewsCardPreview() {
    GoogleNewsCard(
        NewsItemJSON(
            "Peru",
            "peru",
            "Peru",
            "https://www.infoxperu.com/noticias",
            "Peru",
            NewsSourceJSON("https://www.infoxperu.com/noticias")
        ),
        context = LocalContext.current
    )
}
