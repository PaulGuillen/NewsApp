package com.devpaul.infoxperu.domain.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.domain.models.res.Article

@Composable
fun ArticleImage(article: Article) {
    val imagePainter = rememberAsyncImagePainter(
        model = article.socialImage,
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
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = contentScale
    )
}

@Preview(showBackground = true)
@Composable
fun ArticleImagePreview() {
    ArticleImage(
        Article(
            "https://www.deperu.com/tv/wQfz0Keo-Os.venezuela-vs-canada-penales-resumen-y-goles-copa-america-2024-libero.UCk2OZrA0E6q6xp4bBKtf9KA.html",
            "",
            "Video : ? VENEZUELA VS CANADÁ PENALES , RESUMEN Y GOLES - COPA AMÉRICA 2024",
            "20240707T020000Z",
            "https://i.ytimg.com/vi/wQfz0Keo-Os/hqdefault.jpg",
            "deperu.com",
            "Galician",
            "United States",
        ),
    )
}