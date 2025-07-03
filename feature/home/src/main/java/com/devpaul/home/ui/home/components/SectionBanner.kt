package com.devpaul.home.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.PinkGray

@Composable
fun SectionBanner(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PinkGray)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_world),
            contentDescription = "Mundo",
            modifier = Modifier
                .size(80.dp)
                .offset { IntOffset(0, 0) }
                .zIndex(1f)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            tonalElevation = 2.dp,
            shadowElevation = 2.dp,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SectionItem(
                        iconRes = R.drawable.ic_news,
                        label = "Noticias"
                    )
                    SectionItem(
                        iconRes = R.drawable.ic_services,
                        label = "Servicios"
                    )
                    SectionItem(
                        iconRes = R.drawable.ic_districts,
                        label = "Distritos"
                    )
                }

                HorizontalDivider(thickness = 1.5.dp, color = Color.LightGray)

                Text(
                    text = "Secciones disponibles",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp),
                    color = Black
                )
            }
        }
    }
}

@Composable
private fun SectionItem(iconRes: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(52.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontSize = 14.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun SectionBannerPreview() {
    SectionBanner()
}