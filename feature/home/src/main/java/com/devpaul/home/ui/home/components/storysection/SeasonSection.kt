package com.devpaul.home.ui.home.components.storysection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.entity.SeasonDetailEntity
import com.devpaul.home.domain.entity.SeasonEntity
import com.devpaul.shared.domain.SPANISH_DATE_FORMATTER
import com.devpaul.shared.domain.toLocalDate
import java.time.LocalDate

@Composable
fun SeasonSection(seasonState: ResultState<SeasonEntity>) {

    val currentYear = LocalDate.now().year.toString()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.75f
    val cardHeight = 200.dp // 🔥 MISMO ALTO PARA TODOS

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ShowChart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Estaciones",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            when (seasonState) {

                is ResultState.Loading -> {
                    items(4) {
                        SeasonLoadingCard(cardWidth, cardHeight)
                    }
                }

                is ResultState.Success -> {

                    val seasonCards =
                        seasonState.response.toSeasonCards(currentYear)

                    if (seasonCards.isNotEmpty()) {

                        items(seasonCards) { item ->
                            SeasonStoryCard(
                                item = item,
                                cardWidth = cardWidth,
                                cardHeight = cardHeight
                            )
                        }

                    } else {

                        item {
                            SeasonErrorCard(
                                message = "No hay estaciones disponibles para el año $currentYear.",
                                cardWidth = cardWidth,
                                cardHeight = cardHeight
                            )
                        }
                    }
                }

                is ResultState.Error -> {
                    item {
                        SeasonErrorCard(
                            message = seasonState.message.ifBlank {
                                "No se pudo cargar la información estacional."
                            },
                            cardWidth = cardWidth,
                            cardHeight = cardHeight
                        )
                    }
                }

                else -> {
                    item {
                        SeasonErrorCard(
                            message = "Estado desconocido.",
                            cardWidth = cardWidth,
                            cardHeight = cardHeight
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SeasonStoryCard(
    item: SeasonCardUi,
    cardWidth: Dp,
    cardHeight: Dp
) {
    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight) // 🔥 consistente
            .widthIn(min = 240.dp, max = 420.dp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = item.gradientColors)
                )
                .padding(20.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.18f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (item.isCurrent) "ACTUAL" else "ESTACION",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                        tint = Color.White
                    )
                }

                Column {
                    Text(
                        text = item.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Inicio: ${item.startText}",
                        color = Color.White.copy(alpha = 0.92f),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Finaliza: ${item.endText}",
                        color = Color.White.copy(alpha = 0.92f),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = item.scheduleText,
                        color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

/* ---------------- STATES ---------------- */

@Composable
private fun SeasonLoadingCard(cardWidth: Dp, cardHeight: Dp) {
    SeasonFallbackCard(
        message = "Cargando estación...",
        cardWidth = cardWidth,
        cardHeight = cardHeight
    )
}

@Composable
private fun SeasonErrorCard(
    message: String,
    cardWidth: Dp,
    cardHeight: Dp
) {
    SeasonFallbackCard(
        message = message,
        cardWidth = cardWidth,
        cardHeight = cardHeight
    )
}

@Composable
private fun SeasonFallbackCard(
    message: String,
    cardWidth: Dp,
    cardHeight: Dp
) {
    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .widthIn(min = 240.dp, max = 420.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF334155),
                        Color(0xFF475569)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            ),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // 🔥 centrado total
        ) {
            Text(
                text = message,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

/* ---------------- MAPPERS ---------------- */

private fun SeasonEntity.toSeasonCards(currentYear: String): List<SeasonCardUi> {
    val year = years[currentYear] ?: years.entries.maxByOrNull {
        it.key.toIntOrNull() ?: Int.MIN_VALUE
    }?.value ?: return emptyList()

    val now = LocalDate.now()

    return listOfNotNull(
        year.autumn?.toSeasonCardUi(
            "Otoño",
            Icons.Default.WbSunny,
            listOf(Color(0xFFB45309), Color(0xFFF59E0B)),
            now
        ),
        year.winter?.toSeasonCardUi(
            "Invierno",
            Icons.Default.AcUnit,
            listOf(Color(0xFF0F172A), Color(0xFF2563EB)),
            now
        ),
        year.spring?.toSeasonCardUi(
            "Primavera",
            Icons.Default.LocalFlorist,
            listOf(Color(0xFF166534), Color(0xFF4ADE80)),
            now
        ),
        year.summer?.toSeasonCardUi(
            "Verano",
            Icons.Default.WbSunny,
            listOf(Color(0xFFE7CF0E), Color(0xFFB8DE28)),
            now
        )
    )
}

private fun SeasonDetailEntity.toSeasonCardUi(
    name: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    now: LocalDate
): SeasonCardUi {

    val start = startDate.toLocalDate()
    val end = endDate.toLocalDate()

    return SeasonCardUi(
        name = name,
        icon = icon,
        gradientColors = gradientColors,
        startText = startText ?: startDate.orEmpty(),
        endText = end?.format(SPANISH_DATE_FORMATTER) ?: endDate.orEmpty(),
        scheduleText = buildString {
            append("Inicia")
            if (!startHour.isNullOrBlank()) {
                append(" a las $startHour")
            }
            if (start != null) {
                append(" el ${start.format(SPANISH_DATE_FORMATTER)}")
            }
        }.ifBlank { "Sin horario disponible" },
        isCurrent = start != null && end != null && !now.isBefore(start) && now.isBefore(end)
    )
}