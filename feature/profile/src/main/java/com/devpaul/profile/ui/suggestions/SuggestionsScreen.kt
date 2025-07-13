package com.devpaul.profile.ui.suggestions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.profile.ui.suggestions.components.CommentCard
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout

@Composable
fun SuggestionsScreen(navController: NavHostController) {

    BaseContentLayout(
        applyBottomPaddingWhenNoFooter = true,
        header = { TopBar(title = "Comentarios") },
        body = { CommentsBody() },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* acción */ }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    )
}

@Composable
fun CommentsBody() {
    val comments = listOf(
        "Este artículo me pareció excelente.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        comments.forEach { comment ->
            CommentCard(
                name = "Juan Pérez",
                timeAgo = "2h ago",
                comment = comment,
                likes = 0
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestionsScreenPreview() {
    SuggestionsScreen(navController = rememberNavController())
}