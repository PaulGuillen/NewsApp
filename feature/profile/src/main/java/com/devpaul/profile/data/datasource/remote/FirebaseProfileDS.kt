package com.devpaul.profile.data.datasource.remote

import com.devpaul.core_data.util.Constant
import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.mapper.getLikedByMap
import com.devpaul.profile.data.datasource.mapper.toCommentDataEntity
import com.devpaul.profile.data.datasource.mapper.toFirestoreMap
import com.devpaul.profile.data.datasource.mapper.toGetCommentDataEntity
import com.devpaul.profile.data.datasource.mapper.toPostEntity
import com.devpaul.profile.data.datasource.mapper.toProfileEntity
import com.devpaul.profile.data.datasource.mapper.valueOrDefault
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.ProfileEntity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory

@Factory
class FirebaseProfileDS(
    private val firestore: FirebaseFirestore
) {

    suspend fun getProfileById(uid: String): ProfileEntity {
        val snapshot = firestore.collection(Constant.USERS_COLLECTION).document(uid).get().await()

        if (!snapshot.exists()) {
            throw NoSuchElementException("No se encontró el perfil del usuario")
        }

        return snapshot.toProfileEntity(uid)
    }

    suspend fun updateUserData(
        uid: String,
        profileUser: UpdateRequest
    ): GenericEntity {
        firestore.collection(Constant.USERS_COLLECTION)
            .document(uid)
            .set(profileUser.toFirestoreMap(uid), SetOptions.merge())
            .await()

        return GenericEntity(
            status = 200,
            message = "Perfil actualizado correctamente"
        )
    }

    suspend fun createComment(
        commentRequest: CommentRequest
    ): CommentEntity {
        val commentRef = firestore.collection(Constant.COMMENTS_COLLECTION).document()

        commentRef.set(
            hashMapOf(
                COMMENT_ID_FIELD to commentRef.id,
                USER_ID_FIELD to commentRequest.userId.valueOrDefault(),
                NAME_FIELD to commentRequest.name.valueOrDefault(),
                LASTNAME_FIELD to commentRequest.lastname.valueOrDefault(),
                IMAGE_FIELD to commentRequest.image.orEmpty(),
                COMMENT_FIELD to commentRequest.comment.valueOrDefault(),
                CREATED_AT_FIELD to FieldValue.serverTimestamp(),
                LIKES_FIELD to 0
            )
        ).await()

        val snapshot = commentRef.get().await()

        return CommentEntity(
            status = 200,
            message = "Comentario registrado correctamente",
            commentId = commentRef.id,
            data = snapshot.toCommentDataEntity()
        )
    }

    suspend fun getComments(
        limit: Int,
        lastTimestamp: Long? = null
    ): GetCommentEntity {
        var query: Query = firestore.collection(Constant.COMMENTS_COLLECTION)
            .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
            .limit(limit.toLong())

        if (lastTimestamp != null) {
            query = query.startAfter(Timestamp(lastTimestamp, 0))
        }

        val snapshot = query.get().await()
        val comments = snapshot.documents.map { it.toGetCommentDataEntity() }
        val nextCursor = snapshot.documents
            .lastOrNull()
            ?.getTimestamp(CREATED_AT_FIELD)
            ?.seconds
            ?.takeIf { snapshot.size() >= limit }

        return GetCommentEntity(
            status = 200,
            comments = comments,
            nextPageCursor = nextCursor
        )
    }

    suspend fun getPost(): PostEntity {
        val snapshot = firestore.collection(Constant.POSTS_COLLECTION)
            .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
            .get()
            .await()

        return snapshot.documents.toPostEntity()
    }

    suspend fun incrementLike(
        type: String,
        commentId: String,
        userId: String,
        increment: Boolean
    ): GenericEntity {
        val collection = type.toCollectionName()
        val idField = collection.toIdField()
        val document = findTargetDocument(collection, idField, commentId)
            ?: throw NoSuchElementException("No se encontró el registro a actualizar")

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(document)
            val currentLikes = snapshot.getLong(LIKES_FIELD)?.toInt() ?: 0
            val likedBy = snapshot.getLikedByMap().toMutableMap()

            val alreadyLiked = likedBy[userId] == true
            val shouldIncrement = increment && !alreadyLiked
            val shouldDecrement = !increment && alreadyLiked

            if (shouldIncrement) {
                likedBy[userId] = true
                transaction.update(document, buildLikePayload(currentLikes + 1, likedBy))
            } else if (shouldDecrement) {
                likedBy.remove(userId)
                transaction.update(
                    document,
                    buildLikePayload((currentLikes - 1).coerceAtLeast(0), likedBy)
                )
            } else {
                // No se realiza ninguna actualización si el estado de like no cambia
                return@runTransaction
            }
        }.await()

        return GenericEntity(
            status = 200,
            message = "Like actualizado correctamente"
        )
    }

    private suspend fun findTargetDocument(
        collection: String,
        idField: String,
        targetId: String
    ): DocumentReference? {
        val byDocumentId = firestore.collection(collection).document(targetId).get().await()
        if (byDocumentId.exists()) {
            return byDocumentId.reference
        }

        return firestore.collection(collection)
            .whereEqualTo(idField, targetId)
            .limit(1)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.reference
    }

    private fun buildLikePayload(
        likes: Int,
        likedBy: Map<String, Boolean>
    ): Map<String, Any> {
        return mapOf(
            LIKES_FIELD to likes,
            LIKED_BY_FIELD to likedBy
        )
    }

    private fun String.toCollectionName(): String {
        return when (lowercase()) {
            "post", "posts" -> Constant.POSTS_COLLECTION
            else -> Constant.COMMENTS_COLLECTION
        }
    }

    private fun String.toIdField(): String {
        return when (this) {
            Constant.POSTS_COLLECTION -> POST_ID_FIELD
            else -> COMMENT_ID_FIELD
        }
    }

    private companion object {
        const val COMMENT_ID_FIELD = "commentId"
        const val POST_ID_FIELD = "postId"
        const val USER_ID_FIELD = "userId"
        const val NAME_FIELD = "name"
        const val LASTNAME_FIELD = "lastname"
        const val IMAGE_FIELD = "image"
        const val COMMENT_FIELD = "comment"
        const val CREATED_AT_FIELD = "createdAt"
        const val LIKES_FIELD = "likes"
        const val LIKED_BY_FIELD = "likedBy"
    }
}
