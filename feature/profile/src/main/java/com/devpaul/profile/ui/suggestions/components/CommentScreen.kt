package com.devpaul.profile.ui.suggestions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_data.util.Constant
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.CreatedAtEntity
import com.devpaul.profile.domain.entity.GetCommentDataEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.domain.formatRelativeTime
import com.devpaul.shared.ui.components.atoms.base.image.ProfileImagePicker

@Composable
fun CommentScreen(
    comment: ResultState<GetCommentEntity>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit
) {
    when (comment) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.GET_COMMENT)
        }

        is ResultState.Success -> {
            val comments = comment.response.comments
            val hasNextPage = comment.response.nextPageCursor != null
            val listState = rememberLazyListState()

            val shouldLoadMore = remember {
                derivedStateOf {
                    val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    lastVisibleIndex == comments.lastIndex
                }
            }

            LaunchedEffect(shouldLoadMore.value, isLoadingMore, hasNextPage) {
                if (shouldLoadMore.value && !isLoadingMore && hasNextPage) {
                    onLoadMore()
                }
            }

            LazyColumn(
                modifier = Modifier.padding(8.dp),
                state = listState
            ) {
                items(comments) { commentData ->
                    CommentCard(commentData = commentData)
                }

                if (isLoadingMore) {
                    item {
                        Text(
                            "Cargando más...",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        is ResultState.Error -> {
            Text("Error al cargar comentarios")
        }

        else -> Unit
    }
}

@Composable
fun CommentCard(commentData: GetCommentDataEntity) {
    var showDialog by remember { mutableStateOf(false) }
    var isTextOverflow by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                if (!commentData.image.isNullOrEmpty()) {
                    ProfileImagePicker(
                        defaultImageUrl = Constant.URL_IMAGE,
                        base64Image = commentData.image,
                        modifier = Modifier.size(48.dp),
                        isCircular = true,
                        showDialogOnClick = false,
                        onImageSelected = { _, _ -> }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Avatar",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                val dateFormat = formatRelativeTime(
                    seconds = commentData.createdAt.seconds,
                    nanoseconds = commentData.createdAt.nanoseconds
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text("${commentData.name} ${commentData.lastname}")
                    Text(dateFormat, style = MaterialTheme.typography.bodySmall)
                }

                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"${commentData.comment}\"",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { result -> isTextOverflow = result.hasVisualOverflow },
                modifier = Modifier.fillMaxWidth()
            )

            if (isTextOverflow) {
                Text(
                    "Ver más",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { showDialog = true }
                )
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("${commentData.name} ${commentData.lastname}") },
                    text = { Text("\"${commentData.comment}\"") },
                    confirmButton = {
                        Text(
                            "Cerrar", modifier = Modifier
                                .padding(8.dp)
                                .clickable { showDialog = false })
                    },
                    tonalElevation = 0.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentScreenPreview() {
    CommentScreen(
        comment = ResultState.Success(
            GetCommentEntity(
                status = 200,
                comments = listOf(
                    GetCommentDataEntity(
                        id = "1",
                        commentId = "comment_1",
                        userId = "user_1",
                        name = "John",
                        lastname = "Doe",
                        image = null,
                        comment = "This is a sample comment that goes on and on to check overflow behavior in the preview.",
                        createdAt = CreatedAtEntity(seconds = 1633072800, nanoseconds = 0),
                        likes = 5
                    )
                ),
                nextPageCursor = null
            )
        ),
        isLoadingMore = false,
        onLoadMore = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CommentScreenLoadingPreview() {
    CommentScreen(
        comment = ResultState.Loading,
        isLoadingMore = false,
        onLoadMore = {}
    )
}