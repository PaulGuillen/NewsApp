package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.AllPostsResponse
import com.devpaul.profile.data.datasource.dto.res.PostItemResponse
import com.devpaul.profile.domain.entity.AllPostEntity
import com.devpaul.profile.domain.entity.PostItemEntity

fun AllPostsResponse.toDomain(): AllPostEntity {
    return AllPostEntity(
        status = this.status,
        posts = this.posts.map { it.toDomain() },
        data = this.data.map { it.toDomain() }
    )
}

fun PostItemResponse.toDomain(): PostItemEntity {
    return PostItemEntity(
        id = this.id,
        commentId = this.commentId,
        userId = this.userId,
        name = this.name,
        lastname = this.lastname,
        image = this.image,
        comment = this.comment,
        createdAt = createdAt?.toDomain(),
        likes = this.likes ?: 0,
        postId = this.postId,
        title = this.title,
        description = this.description,
        toPublic = this.toPublic ?: false
    )
}