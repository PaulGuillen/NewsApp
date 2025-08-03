package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.GetCommentDataResponse
import com.devpaul.profile.data.datasource.dto.res.GetCommentResponse
import com.devpaul.profile.domain.entity.GetCommentDataEntity
import com.devpaul.profile.domain.entity.GetCommentEntity

fun GetCommentResponse.toDomain(): GetCommentEntity {
    return GetCommentEntity(
        status = this.status,
        comments = this.comments.map { it.toDomain() },
        nextPageCursor = this.nextPageCursor,
    )
}

fun GetCommentDataResponse.toDomain(): GetCommentDataEntity {
    return GetCommentDataEntity(
        id = this.id,
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
