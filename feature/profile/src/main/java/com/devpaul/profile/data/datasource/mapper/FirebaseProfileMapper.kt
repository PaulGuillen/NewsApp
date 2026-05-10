package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.CommentDataEntity
import com.devpaul.profile.domain.entity.CreatedAtEntity
import com.devpaul.profile.domain.entity.GetCommentDataEntity
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.PostItemEntity
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toProfileEntity(uid: String): ProfileEntity {
    return ProfileEntity(
        status = 200,
        message = "Perfil obtenido correctamente",
        data = toProfileUserEntity(uid)
    )
}

fun DocumentSnapshot.toProfileUserEntity(uid: String): ProfileUserEntity {
    return ProfileUserEntity(
        id = id,
        uid = getString("uid").orEmpty().ifBlank { uid },
        name = getString("name").orEmpty(),
        lastname = getString("lastname").orEmpty(),
        phone = getString("phone").orEmpty(),
        birthdate = getString("birthdate").orEmpty(),
        email = getString("email").orEmpty(),
        password = getString("password").orEmpty(),
        image = getString("image")
    )
}

fun UpdateRequest.toFirestoreMap(uid: String): Map<String, Any?> {
    return mapOf(
        "uid" to uid,
        "name" to name,
        "lastname" to lastname,
        "birthdate" to birthdate,
        "phone" to phone,
        "email" to email,
        "password" to password,
        "image" to image
    )
}

fun List<DocumentSnapshot>.toPostEntity(): PostEntity {
    return PostEntity(
        status = 200,
        data = map { it.toPostItemEntity() }.filter { it.toPublic == true }
    )
}

fun DocumentSnapshot.toCommentDataEntity(): CommentDataEntity {
    val createdAt = getTimestamp("createdAt")
    return CommentDataEntity(
        commentId = getString("commentId").orEmpty().ifBlank { id },
        userId = getString("userId").valueOrDefault(),
        name = getString("name").valueOrDefault(),
        lastname = getString("lastname").valueOrDefault(),
        image = getString("image").orEmpty().ifBlank { null },
        comment = getString("comment").valueOrDefault(),
        createdAt = CreatedAtEntity(
            seconds = createdAt?.seconds ?: 0L,
            nanoseconds = createdAt?.nanoseconds ?: 0
        ),
        likes = getLong("likes")?.toInt() ?: 0
    )
}

fun DocumentSnapshot.toGetCommentDataEntity(): GetCommentDataEntity {
    val createdAt = getTimestamp("createdAt")
    return GetCommentDataEntity(
        id = id,
        commentId = getString("commentId").orEmpty().ifBlank { id },
        userId = getString("userId").valueOrDefault(),
        name = getString("name").valueOrDefault(),
        lastname = getString("lastname").valueOrDefault(),
        image = getString("image").orEmpty().ifBlank { null },
        comment = getString("comment").valueOrDefault(),
        createdAt = CreatedAtEntity(
            seconds = createdAt?.seconds ?: 0L,
            nanoseconds = createdAt?.nanoseconds ?: 0
        ),
        likes = getLong("likes")?.toInt() ?: 0
    )
}

fun DocumentSnapshot.toPostItemEntity(): PostItemEntity {
    val createdAt = getTimestamp("createdAt")
    return PostItemEntity(
        id = id,
        createdAt = createdAt?.toCreatedAtEntity(),
        likes = getLong("likes")?.toInt() ?: 0,
        postId = getString("postId").orEmpty().ifBlank { id },
        title = getString("title").valueOrDefault(),
        description = getString("description").valueOrDefault(),
        toPublic = getBoolean("toPublic") ?: false,
        image = getString("image").orEmpty().ifBlank { null },
        likedBy = getLikedByMap()
    )
}

fun DocumentSnapshot.getLikedByMap(): Map<String, Boolean> {
    return (get("likedBy") as? Map<*, *>)
        ?.mapNotNull { (key, value) ->
            (key as? String)?.let { safeKey -> safeKey to (value as? Boolean ?: false) }
        }
        ?.toMap()
        ?: emptyMap()
}

fun Timestamp.toCreatedAtEntity(): CreatedAtEntity {
    return CreatedAtEntity(
        seconds = seconds,
        nanoseconds = nanoseconds
    )
}

fun String?.valueOrDefault(): String = this?.takeIf { it.isNotBlank() } ?: "Sin registrar"