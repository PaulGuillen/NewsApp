package com.devpaul.profile.ui.suggestions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devpaul.profile.ui.suggestions.components.CommentScreen
import com.devpaul.profile.ui.suggestions.components.PostScreen
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
        isBodyScrollable = true,
        body = {
            CommentsBody(
                uiState = uiState
            )
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
fun CommentsBody(
    uiState: SuggestionUiState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostScreen(
            post = uiState.posts,
            onLikeClick = {
            },
            onBackClick = {

            }

        )

        HorizontalDivider(thickness = 1.5.dp, color = Color.LightGray)

        Text(
            text = "Sugerencias y comentarios",
            fontSize = 14.sp,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )

        HorizontalDivider(thickness = 1.5.dp, color = Color.LightGray)

        CommentScreen(
            comment = uiState.getComments,
        )
    }
}

@Composable
fun CommentsFooter(
    commentText: String,
    onCommentChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
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