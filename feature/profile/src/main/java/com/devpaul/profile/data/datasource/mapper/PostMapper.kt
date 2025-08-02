package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.PostResponse
import com.devpaul.profile.data.datasource.dto.res.PostItemResponse
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.PostItemEntity

fun PostResponse.toDomain(): PostEntity {
    return PostEntity(
        status = this.status,
        data = this.data.map { it.toDomain() }
    )
}

fun PostItemResponse.toDomain(): PostItemEntity {
    return PostItemEntity(
        id = this.id,
        image = this.image,
        createdAt = createdAt?.toDomain(),
        likes = this.likes ?: 0,
        postId = this.postId,
        title = this.title,
        description = this.description,
        toPublic = this.toPublic ?: false,
        likedBy = this.likedBy ?: emptyMap()
    )
}