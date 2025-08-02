package com.devpaul.profile.ui.suggestions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devpaul.core_data.util.Constant
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.ui.suggestions.components.CommentScreen
import com.devpaul.profile.ui.suggestions.components.PostScreen
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.atoms.base.image.ProfileImagePicker
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SuggestionsScreen(navController: NavHostController) {

    val viewModel: SuggestionViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        onInit = { _, _ ->
            viewModel.getProfileData()
        },
        onUiEvent = { event, _ ->
            when (event) {
                is SuggestionUiEvent.NavigationBack -> {
                    navController.popBackStack()
                }
            }
        },
        navController = navController,
    ) { _, uiState, onIntent, showSnackBar, _ ->
        SuggestionContent(
            uiState = uiState,
            showSnackBar = showSnackBar,
            onIntent = onIntent,
        )
    }
}

@Composable
fun SuggestionContent(
    uiState: SuggestionUiState,
    showSnackBar: (String) -> Unit,
    onIntent: (SuggestionUiIntent) -> Unit,
) {
    var commentText by remember { mutableStateOf("") }
    val createCommentState = uiState.createComment

    LaunchedEffect(createCommentState) {
        if (createCommentState is ResultState.Success) {
            commentText = ""
            onIntent(SuggestionUiIntent.GetComments)
        }
    }

    when (uiState.createComment) {
        is ResultState.Loading -> {
            ScreenLoading()
        }

        is ResultState.Error -> {
            showSnackBar("Error al crear el comentario")
        }

        else -> {
            // Handle other states if needed
        }
    }

    BaseContentLayout(
        isBodyScrollable = true,
        applyStatusBarsPaddingToHeader = true,
        body = {
            CommentsBody(
                uiState = uiState,
                onIntent = onIntent
            )
        },
        footer = {
            CommentsFooter(
                uiState = uiState,
                commentText = commentText,
                onCommentChange = { commentText = it },
                onSendClick = {
                    if (commentText.isNotBlank()) {
                        onIntent(
                            SuggestionUiIntent.CreateComment(
                                userId = uiState.profile?.id ?: "",
                                name = uiState.profile?.name ?: "",
                                lastname = uiState.profile?.lastname ?: "",
                                image = uiState.profile?.image ?: "",
                                comment = commentText
                            )
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun CommentsBody(
    uiState: SuggestionUiState,
    onIntent: (SuggestionUiIntent) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PostScreen(
            post = uiState.posts,
            userId = uiState.profile?.id.orEmpty(),
            onLikeClick = { post, increment ->
                onIntent(
                    SuggestionUiIntent.PatchIncrementLike(
                        type = "post",
                        commentId = post.postId.orEmpty(),
                        userId = uiState.profile?.id.orEmpty(),
                        increment = increment
                    )
                )
            },
            onBackClick = {
                onIntent(SuggestionUiIntent.NavigateBack)
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
    uiState: SuggestionUiState,
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
                val profile = uiState.profile ?: return@Row

                ProfileImagePicker(
                    defaultImageUrl = Constant.URL_IMAGE,
                    base64Image = profile.image,
                    modifier = Modifier.size(42.dp),
                    isCircular = true,
                    showDialogOnClick = false,
                    onImageSelected = { _, _ -> }
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
        showSnackBar = {},
        onIntent = {}
    )
}