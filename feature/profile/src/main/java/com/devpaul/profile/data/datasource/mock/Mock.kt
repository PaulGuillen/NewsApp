package com.devpaul.profile.data.datasource.mock

import com.devpaul.profile.domain.entity.CreatedAtEntity
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.PostItemEntity

data class PostMock(
    val postMock: PostEntity = PostEntity(
        status = 200,
        data = listOf(
            PostItemEntity(
                id = "1",
                createdAt = CreatedAtEntity(seconds = 1633072800, nanoseconds = 0),
                likes = 10,
                postId = "post_1",
                title = "First Post",
                description = "This is the first post description.",
                toPublic = true,
                image = "https://example.com/image1.jpg"
            ),
        )
    )
)