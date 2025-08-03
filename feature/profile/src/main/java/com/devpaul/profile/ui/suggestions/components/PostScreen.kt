package com.devpaul.profile.ui.suggestions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.data.datasource.mock.PostMock
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.PostItemEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import kotlinx.coroutines.delay

@Composable
fun PostScreen(
    post: ResultState<PostEntity>,
    userId: String,
    onLikeClick: (PostItemEntity, Boolean) -> Unit,
    onBackClick: () -> Unit,
) {
    when (post) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.POST_SCREEN)
        }

        is ResultState.Success -> {
            val data = post.response.data.firstOrNull() ?: return
            var isLiked by remember { mutableStateOf(data.likedBy?.get(userId) == true) }
            var likeCount by remember { mutableStateOf(data.likes ?: 0) }
            var isProcessing by remember { mutableStateOf(false) }

            LaunchedEffect(isProcessing) {
                if (isProcessing) {
                    delay(1000)
                    isProcessing = false
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberAsyncImagePainter(data.image),
                        contentDescription = data.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .padding(8.dp)
                                .clickable { onBackClick() }
                        )

                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isLiked) Color.Red else Color.Gray,
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .padding(8.dp)
                                .clickable(enabled = !isProcessing) {
                                    isLiked = !isLiked
                                    likeCount = if (isLiked) likeCount + 1 else likeCount - 1
                                    isProcessing = true
                                    onLikeClick(data, isLiked)
                                }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Text(
                        text = data.title ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "$likeCount me gusta",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 4.dp)
                    )

                    val description = data.description.orEmpty()
                    val lineBreaks = remember(description) { description.count { it == '\n' } }

                    val shouldForceShowMore = lineBreaks >= 3
                    var isExpanded by remember { mutableStateOf(false) }
                    var isOverflowing by remember { mutableStateOf(false) }

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { result ->
                            isOverflowing = result.lineCount > 1
                        }
                    )

                    if (shouldForceShowMore || isOverflowing) {
                        Text(
                            text = if (isExpanded) "Ver menos" else "Ver más",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .clickable { isExpanded = !isExpanded }
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }

        is ResultState.Error -> {
            Text(
                text = "Error al cargar el post.",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> Unit
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {
    val mockPost = ResultState.Success(PostMock().postMock)
    PostScreen(
        post = mockPost,
        userId = "",
        onLikeClick = { _, _ -> },
        onBackClick = {}
    )
}