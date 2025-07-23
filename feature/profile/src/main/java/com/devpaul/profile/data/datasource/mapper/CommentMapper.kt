package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.CommentDataResponse
import com.devpaul.profile.data.datasource.dto.res.CommentResponse
import com.devpaul.profile.data.datasource.dto.res.CreatedAtResponse
import com.devpaul.profile.domain.entity.CommentDataEntity
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.CreatedAtEntity

fun CommentResponse.toDomain(): CommentEntity {
    return CommentEntity(
        status = this.status,
        message = this.message,
        commentId = this.commentId,
        data = this.data.toDomain()
    )
}

fun CommentDataResponse.toDomain(): CommentDataEntity {
    return CommentDataEntity(
        commentId = this.commentId,
        userId = this.userId,
        name = this.name,
        lastname = this.lastname,
        image = this.image,
        comment = this.comment,
        createdAt = this.createdAt.toDomain(),
        likes = this.likes
    )
}

fun CreatedAtResponse.toDomain(): CreatedAtEntity {
    return CreatedAtEntity(
        seconds = this.seconds,
        nanoseconds = this.nanoseconds
    )
}