package com.devpaul.profile.ui.suggestions

import com.devpaul.core_data.serialization.Wrapper
import com.devpaul.core_data.serialization.fromJsonGeneric
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.profile.domain.usecase.CreateCommentUC
import com.devpaul.profile.domain.usecase.GetCommentUC
import com.devpaul.profile.domain.usecase.GetPostUC
import com.devpaul.profile.domain.usecase.IncrementLikeUC
import com.google.gson.Gson
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SuggestionViewModel(
    private val getPostUC: GetPostUC,
    private val getCommentUC: GetCommentUC,
    private val patchIncrementLike: IncrementLikeUC,
    private val createCommentUC: CreateCommentUC,
    private val dataStoreUseCase: DataStoreUseCase,
) : StatefulViewModel<SuggestionUiState, SuggestionUiIntent, SuggestionUiEvent>(
    defaultUIState = {
        SuggestionUiState()
    }
) {

    var profile: ProfileUserEntity? = null

    init {
        SuggestionUiIntent.GetPost.execute()
        SuggestionUiIntent.GetComments(isNextPage = false).execute()
    }

    override suspend fun onUiIntent(intent: SuggestionUiIntent) {
        when (intent) {
            is SuggestionUiIntent.CreateComment -> launchIO {
                createComment(
                    userId = intent.userId,
                    name = intent.name,
                    lastname = intent.lastname,
                    image = intent.image,
                    comment = intent.comment,
                )
            }

            is SuggestionUiIntent.PatchIncrementLike -> launchIO {
                incrementLike(
                    type = intent.type,
                    commentId = intent.commentId,
                    userId = intent.userId,
                    increment = intent.increment,
                )
            }

            is SuggestionUiIntent.GetPost -> launchIO {
                fetchPost()
            }

            is SuggestionUiIntent.GetComments -> launchIO {
                fetchComments(intent.isNextPage)
            }

            is SuggestionUiIntent.NavigateBack -> navigationBack()
        }
    }

    suspend fun getProfileData() {
        val profileJson = dataStoreUseCase.getString("profile_data")
        val wrapper: Wrapper<ProfileUserEntity>? = profileJson
            ?.takeIf { it.isNotBlank() }
            ?.let { Gson().fromJsonGeneric(it) }
        profile = wrapper?.data

        updateUiStateOnMain {
            it.copy(profile = profile)
        }
    }

    private suspend fun createComment(
        userId: String,
        name: String,
        lastname: String,
        image: String,
        comment: String
    ) {
        updateUiStateOnMain { it.copy(createComment = ResultState.Loading) }

        val request = CommentRequest(
            userId = userId,
            name = name,
            lastname = lastname,
            image = image,
            comment = comment
        )

        when (val result = createCommentUC.createComment(request)) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(createComment = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        createComment = ResultState.Error(
                            message = result.error.message ?: ERROR_CREATE_COMMENT
                        )
                    )
                }
            }
        }
    }

    private suspend fun incrementLike(
        type: String,
        commentId: String,
        userId: String,
        increment: Boolean,
    ) {
        updateUiStateOnMain { it.copy(incrementLike = ResultState.Loading) }

        when (
            val result = patchIncrementLike.incrementLike(
                type = type,
                commentId = commentId,
                userId = userId,
                increment = increment
            )
        ) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(incrementLike = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        incrementLike = ResultState.Error(
                            message = result.error.message ?: ERROR_INCREMENT_LIKE
                        )
                    )
                }
            }
        }
    }

    private suspend fun fetchPost() {
        updateUiStateOnMain { it.copy(posts = ResultState.Loading) }

        when (val result = getPostUC.getPost()) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(posts = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        posts = ResultState.Error(
                            message = result.error.message ?: ERROR_GET_POSTS
                        )
                    )
                }
            }
        }
    }

    private suspend fun fetchComments(isNextPage: Boolean = false) {
        val cursor = uiState.nextPageCursor
        updateUiStateOnMain { it.copy(isLoadingMore = true) }

        when (
            val result = getCommentUC.getComments(
                limit = 10,
                lastTimestamp = if (isNextPage) cursor else null
            )
        ) {
            is Output.Success -> {
                val oldList = if (isNextPage) {
                    (uiState.getComments as? ResultState.Success)?.response?.comments.orEmpty()
                } else {
                    emptyList()
                }
                val newList = oldList + result.data.comments
                val newCursor = result.data.nextPageCursor

                updateUiStateOnMain { state ->
                    state.copy(
                        getComments = ResultState.Success(
                            result.data.copy(comments = newList)
                        ),
                        nextPageCursor = newCursor,
                        isLoadingMore = false
                    )
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { state ->
                    state.copy(
                        isLoadingMore = false,
                        getComments = if (!isNextPage) {
                            ResultState.Error(result.error.message ?: ERROR_GET_COMMENTS)
                        } else {
                            state.getComments
                        }
                    )
                }
            }
        }
    }

    private fun navigationBack() {
        SuggestionUiEvent.NavigationBack.send()
    }

    private companion object {
        const val ERROR_CREATE_COMMENT = "An error occurred while creating comment."
        const val ERROR_INCREMENT_LIKE = "An error occurred while incrementing like."
        const val ERROR_GET_POSTS = "An error occurred while fetching all posts."
        const val ERROR_GET_COMMENTS = "An error occurred while fetching comments."
    }
}
