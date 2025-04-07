package com.devpaul.infoxperu.feature.util.ui.news_screen

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.GreenLight
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun CountryCard(
    country: Country,
    isSelected: Boolean,
    context: Context,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(0.8.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) GreenLight else White,
            contentColor = Black
        ),
        onClick = {
            onClick()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = rememberAsyncImagePainter(country.imageUrl),
                contentDescription = country.title,
                modifier = Modifier
                    .size(60.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = country.title,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionItemPreview() {
    CountryCard(
        Country(
            "Peru",
            "peru",
            "Peru",
            "https://www.infoxperu.com/noticias"
        ),
        isSelected = false,
        context = LocalContext.current
    )
}
