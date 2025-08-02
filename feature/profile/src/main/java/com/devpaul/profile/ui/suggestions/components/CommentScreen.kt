package com.devpaul.profile.ui.suggestions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
) {
    when (comment) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.GET_COMMENT)
        }

        is ResultState.Success -> {
            val data = comment.response.comments

            Column(modifier = Modifier.padding(8.dp)) {
                data.forEach { commentData ->

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
                                        contentDescription = "Default Avatar",
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                val dateFormat = formatRelativeTime(
                                    seconds = commentData.createdAt.seconds,
                                    nanoseconds = commentData.createdAt.nanoseconds
                                )

                                Row(
                                    modifier = Modifier.weight(1f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            text = "${commentData.name} ${commentData.lastname}",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Text(
                                            text = dateFormat,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }

                                    Icon(
                                        imageVector = Icons.Filled.MailOutline,
                                        contentDescription = "Desde dispositivo",
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Column {
                                Text(
                                    text = "\"${commentData.comment}\"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    onTextLayout = { result ->
                                        isTextOverflow = result.hasVisualOverflow
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                if (isTextOverflow) {
                                    Text(
                                        text = "Ver más",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .clickable { showDialog = true }
                                    )
                                }
                            }

                        }
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = {
                                Text(text = "${commentData.name} ${commentData.lastname}")
                            },
                            text = {
                                Text(text = "\"${commentData.comment}\"")
                            },
                            confirmButton = {
                                Text(
                                    text = "Cerrar",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { showDialog = false }
                                )
                            },
                            tonalElevation = 0.dp,
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }

        is ResultState.Error -> {

        }

        else -> Unit
    }
}

@Preview(showBackground = true)
@Composable
fun CommentScreenPreview() {
    CommentScreen(
        comment = ResultState.Success(
            GetCommentEntity(
                0, listOf(
                    GetCommentDataEntity(
                        id = "1",
                        commentId = "comment_1",
                        userId = "user_1",
                        name = "John",
                        lastname = "Doe",
                        image = "",
                        comment = "This is a sample comment.",
                        createdAt = CreatedAtEntity(seconds = 1633072800, nanoseconds = 0),
                        likes = 5
                    )
                )
            )
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun CommentScreenLoadingPreview() {
    CommentScreen(
        comment = ResultState.Loading,
    )
}
