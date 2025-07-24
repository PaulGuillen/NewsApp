package com.devpaul.profile.ui.suggestions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.CreatedAtEntity
import com.devpaul.profile.domain.entity.GetCommentDataEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType

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

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ) {
                    data.forEach { commentData ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {

                            if (!commentData.image.isNullOrEmpty()) {
                                Image(
                                    painter = rememberAsyncImagePainter(commentData.image),
                                    contentDescription = commentData.commentId,
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Default Avatar",
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp),
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = commentData.comment,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                        }
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
                        image = "https://example.com/user1.jpg",
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
