package com.devpaul.news.ui.news.components.country

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.domain.entity.CountryItemEntity

@Composable
fun CountryCard(
    country: CountryItemEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val isDark = isSystemInDarkTheme()

    val background by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF052E2B) else Color.Transparent,
        label = "bg"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected)
            Color(0xFF10D7EE) // BrandAccent
        else
            if (isDark) Color(0xFF1E293B) else Color(0xFFE5E7EB),
        label = "border"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected)
            Color(0xFF10D7EE)
        else if (isDark)
            Color.White
        else
            Color.Black,
        label = "text"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            // 🔥 IMAGEN DESDE BACKEND
            SubcomposeAsyncImage(
                model = country.imageUrl,
                contentDescription = country.title,
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
            ) {
                when (painter.state) {

                    is AsyncImagePainter.State.Success -> {
                        SubcomposeAsyncImageContent()
                    }

                    else -> {
                        // 🔥 AQUÍ USAS ICON
                        Icon(
                            imageVector = Icons.Default.Public, // o Newspaper
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = country.title.uppercase(),
                color = textColor,
                fontSize = 12.sp
            )
        }

        // 🔥 GLOW SOLO EN DARK + SELECTED
        if (isDark && isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Color(0xFF10D7EE).copy(alpha = 0.12f),
                        RoundedCornerShape(50)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryCardPreview() {
    val selectedCountry by remember { mutableStateOf<CountryItemEntity?>(null) }
    CountryCard(
        country = NewsMock().countryMock.data.first(),
        isSelected = selectedCountry?.id == NewsMock().countryMock.data.first().id,
        onClick = {}
    )
}