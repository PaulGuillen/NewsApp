package com.devpaul.news.ui.news.components.news

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.GreenDark

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    context: Context,
    title: String,
    source: String,
    category: String,
    time: String,
    url: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {

    val isDark = isSystemInDarkTheme()

    val titleColor by animateColorAsState(
        targetValue = when {
            isSelected && isDark -> BrandAccent
            isSelected && !isDark -> GreenDark
            isDark -> Color.White
            else -> Color(0xFF0F172A)
        },
        label = "title-color"
    )

    val metaColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
    val divider = if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect()
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, url.toUri())
                )
            }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {

        // 🔥 TITLE
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                lineHeight = 22.sp
            ),
            color = titleColor
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 🔥 META ROW
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            SourceBadge(source)

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = category.uppercase(),
                color = metaColor,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "•",
                color = metaColor
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = time,
                color = metaColor,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // 🔥 BOOKMARK ICON
            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = null,
                tint = metaColor,
                modifier = Modifier.size(18.dp)
            )
        }
    }

    HorizontalDivider(Modifier, thickness = 0.6.dp, color = divider)
}

@Composable
fun SourceBadge(source: String) {

    val color = when (source) {
        "Google" -> Color(0xFF2563EB)
        "Reddit" -> Color(0xFFF97316)
        "Delta Project" -> Color(0xFFE11D48)
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = source,
            color = color,
            fontSize = 10.sp
        )
    }
}

@Preview(name = "NewsCard - Light", showBackground = true)
@Composable
fun NewsCardPreviewLight() {
    NewsCardPreviewContent(isDark = false)
}

@Preview(name = "NewsCard - Dark", showBackground = true)
@Composable
fun NewsCardPreviewDark() {
    NewsCardPreviewContent(isDark = true)
}

@Composable
private fun NewsCardPreviewContent(isDark: Boolean) {
    var selected by remember { mutableStateOf(false) }

    val background = if (isDark)
        Color(0xFF0B0B0F)
    else
        Color(0xFFF5F7FA)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {

        NewsCard(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp),
            context = LocalContext.current,
            title = "Bolsa de Valores de Lima cierra al alza impulsada por sector minero 🌐",
            source = "GOOGLE",
            category = "Gestión",
            time = "15min",
            url = "https://www.example.com",
            isSelected = selected,
            onSelect = { !selected }
        )

        NewsCard(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp),
            context = LocalContext.current,
            title = "Congreso debate nuevas medidas para reactivación de MYPES: Sectores advierten retrasos",
            source = "REDDIT",
            category = "r/perueconomy",
            time = "15min",
            url = "https://www.example.com",
            isSelected = selected,
            onSelect = { !selected }
        )

        NewsCard(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp),
            context = LocalContext.current,
            title = "Retiro de CTS: Todo lo que debes saber antes de disponer de tu dinero",
            source = "DELTA",
            category = "Internal Analysis",
            time = "15min",
            url = "https://www.example.com",
            isSelected = selected,
            onSelect = { !selected }
        )
    }
}