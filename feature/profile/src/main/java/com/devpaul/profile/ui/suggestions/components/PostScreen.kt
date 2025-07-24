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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.data.datasource.mock.PostMock
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType

@Composable
fun PostScreen(
    post: ResultState<PostEntity>,
    onLikeClick: (PostEntity) -> Unit,
) {
    when (post) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.POST_SCREEN)
        }

        is ResultState.Success -> {
            val response = post.response
            val data = response.data.firstOrNull()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            ) {
                Column {
                    Image(
                        painter = rememberAsyncImagePainter(data?.image),
                        contentDescription = data?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(12.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Like",
                                tint = Color.Black
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = (data?.likes).toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

        }

        is ResultState.Error -> {
            // Handle error state
        }

        else -> {
            // Handle idle state
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {
    val mockPost = ResultState.Success(PostMock().postMock)
    PostScreen(post = mockPost, onLikeClick = {})
}