package com.devpaul.profile.ui.suggestions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SuggestionsScreen(navController: NavHostController) {

    val viewModel: SuggestionViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
    ) { _, uiState, onIntent, _, _ ->
        SuggestionContent(
            uiState = uiState,
            onIntent = onIntent,
        )
    }
}

@Composable
fun SuggestionContent(
    uiState: SuggestionUiState,
    onIntent: (SuggestionUiIntent) -> Unit,
) {
    val comments = remember { mutableStateListOf<String>() }
    var commentText by remember { mutableStateOf("") }

    BaseContentLayout(
        isBodyScrollable = false,
        header = { TopBar(title = "Comentarios") },
        body = {
            CommentsBody(comments)
        },
        footer = {
            CommentsFooter(
                commentText = commentText,
                onCommentChange = { commentText = it },
                onSendClick = {
                    if (commentText.isNotBlank()) {
                        comments.add(commentText)
                        commentText = ""
                    }
                }
            )
        }
    )
}

@Composable
fun CommentsBody(comments: List<String>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.LightGray),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "1", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(comments) { comment ->
                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsFooter(
    commentText: String,
    onCommentChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = commentText,
                    onValueChange = onCommentChange,
                    placeholder = { Text("Comentario") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )

                )

                IconButton(onClick = onSendClick) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestionsScreenPreview() {
    SuggestionContent(
        uiState = SuggestionUiState(),
        onIntent = {}
    )
}