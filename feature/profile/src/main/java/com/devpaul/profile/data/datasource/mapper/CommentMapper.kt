package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.CommentDataResponse
import com.devpaul.profile.data.datasource.dto.res.CommentResponse
import com.devpaul.profile.data.datasource.dto.res.CreatedAtResponse
import com.devpaul.profile.domain.entity.CommentDataEntity
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.CreatedAtEntity

fun CommentResponse.toDomain(): CommentEntity {
    return CommentEntity(
        status = status,
        message = message,
        commentId = commentId,
        data = data.toDomain()
    )
}

fun CommentDataResponse.toDomain(): CommentDataEntity {
    return CommentDataEntity(
        commentId = commentId,
        userId = userId,
        name = name,
        lastname = lastname,
        image = image,
        comment = comment,
        createdAt = createdAt.toDomain(),
        likes = likes
    )
}

fun CreatedAtResponse.toDomain(): CreatedAtEntity {
    return CreatedAtEntity(
        seconds = seconds,
        nanoseconds = nanoseconds
    )
}