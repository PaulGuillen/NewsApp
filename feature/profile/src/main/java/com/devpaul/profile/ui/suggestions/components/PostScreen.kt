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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.data.datasource.mock.PostMock
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.PostItemEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType

@Composable
fun PostScreen(
    post: ResultState<PostEntity>,
    onLikeClick: (PostItemEntity) -> Unit,
    onBackClick: () -> Unit,
) {
    when (post) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.POST_SCREEN)
        }

        is ResultState.Success -> {
            val data = post.response.data.firstOrNull()

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberAsyncImagePainter(data?.image),
                        contentDescription = data?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        contentScale = ContentScale.Crop
                    )

                    // Top Buttons Overlay
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
                            tint = Color.Black,
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .padding(8.dp)
                                .clickable {
                                    data?.let { onLikeClick(it) }
                                }
                        )
                    }
                }

                // Post Details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Text(
                        text = data?.title ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = data?.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        is ResultState.Error -> {
            // Manejo de error
        }

        else -> Unit
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {
    val mockPost = ResultState.Success(PostMock().postMock)
    PostScreen(post = mockPost, onLikeClick = {}, onBackClick = {})
}