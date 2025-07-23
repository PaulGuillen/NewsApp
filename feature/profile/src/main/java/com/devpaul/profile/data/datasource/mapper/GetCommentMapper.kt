package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.GetCommentDataResponse
import com.devpaul.profile.data.datasource.dto.res.GetCommentResponse
import com.devpaul.profile.domain.entity.GetCommentDataEntity
import com.devpaul.profile.domain.entity.GetCommentEntity

fun GetCommentResponse.toDomain(): GetCommentEntity {
    return GetCommentEntity(
        status = status,
        comments = comments.toDomain()
    )
}

fun GetCommentDataResponse.toDomain(): GetCommentDataEntity {
    return GetCommentDataEntity(
        id = id,
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
