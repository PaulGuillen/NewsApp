package com.devpaul.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.White
import com.devpaul.home.data.datasource.mock.SectionMock
import com.devpaul.home.domain.entity.SectionDataEntity
import com.devpaul.shared.ui.components.organisms.InfoDialog

@Composable
fun ItemSection(
    sectionItem: SectionDataEntity,
    navController: NavHostController,
) {
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(140.dp)
            .height(64.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(0.8.dp, Color.Black),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Black
        ),
        onClick = {
            showDialog.value = true
        }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = sectionItem.title,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
            )
        }
    }

    if (showDialog.value) {
        InfoDialog(
            title = sectionItem.title,
            description = sectionItem.description,
            imageUrl = sectionItem.imageUrl,
            onDismiss = { showDialog.value = false },
            onNavigate = {
                when (sectionItem.destination.lowercase()) {
                    "districts" -> navController.navigate(Screen.Districts.route)
                    "news" -> navController.navigate(Screen.News.route)
                    "profile" -> navController.navigate(Screen.Profile.route)
                    else -> {}
                }
                showDialog.value = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SectionItemPreview() {
    SectionMock().sectionMock.data.firstOrNull()?.let {
        ItemSection(
            sectionItem = it,
            navController = NavHostController(LocalContext.current)
        )
    }
}