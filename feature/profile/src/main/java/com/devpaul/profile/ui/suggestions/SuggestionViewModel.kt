package com.devpaul.profile.ui.suggestions

import com.devpaul.core_data.serialization.Wrapper
import com.devpaul.core_data.serialization.fromJsonGeneric
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
import timber.log.Timber

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
        SuggestionUiIntent.GetComments.execute()
    }

    override suspend fun onUiIntent(intent: SuggestionUiIntent) {
        when (intent) {
            is SuggestionUiIntent.CreateComment -> {
                launchIO {
                    createComment(
                        userId = intent.userId,
                        name = intent.name,
                        lastname = intent.lastname,
                        image = intent.image,
                        comment = intent.comment,
                    )
                }
            }

            is SuggestionUiIntent.PatchIncrementLike -> {
                launchIO {
                    incrementLike(
                        type = intent.type,
                        commentId = intent.commentId,
                    )
                }
            }

            is SuggestionUiIntent.GetPost -> {
                launchIO {
                    fetchPost()
                }
            }

            is SuggestionUiIntent.GetComments -> {
                launchIO {
                    fetchComments()
                }
            }
        }
    }

    suspend fun getProfileData() {
        val profileJson = dataStoreUseCase.getString("profile_data")
        val wrapper: Wrapper<ProfileUserEntity>? = Gson().fromJsonGeneric(profileJson)
        profile = wrapper?.data

        updateUiStateOnMain {
            it.copy(profile = profile)
        }
        Timber.d("Profile data: $profile")
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
        val result = createCommentUC(CreateCommentUC.Params(createComment = request))
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is CreateCommentUC.Success.CreateCommentSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(createComment = ResultState.Success(it.comment))
                        }
                    }
                }
            }.onFailure<CreateCommentUC.Failure> {
                when (it) {
                    is CreateCommentUC.Failure.CreateCommentError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                createComment = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "An error occurred while creating comment."
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun incrementLike(type: String, commentId: String) {
        updateUiStateOnMain { it.copy(incrementLike = ResultState.Loading) }
        val result = patchIncrementLike(IncrementLikeUC.Params(type = type, commentId = commentId))
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is IncrementLikeUC.Success.IncrementLikeSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(incrementLike = ResultState.Success(it.incrementLike))
                        }
                    }
                }
            }.onFailure<IncrementLikeUC.Failure> {
                when (it) {
                    is IncrementLikeUC.Failure.IncrementLikeError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                incrementLike = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "An error occurred while incrementing like."
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchPost() {
        updateUiStateOnMain { it.copy(posts = ResultState.Loading) }
        val result = getPostUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GetPostUC.Success.AllPostsSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(posts = ResultState.Success(it.allPosts))
                        }
                    }
                }
            }.onFailure<GetPostUC.Failure> {
                when (it) {
                    is GetPostUC.Failure.AllPostsError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                posts = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "An error occurred while fetching all posts."
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchComments() {
        updateUiStateOnMain { it.copy(getComments = ResultState.Loading) }
        val result = getCommentUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GetCommentUC.Success.GetCommentSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(getComments = ResultState.Success(it.comment))
                        }
                    }
                }
            }.onFailure<GetCommentUC.Failure> {
                when (it) {
                    is GetCommentUC.Failure.GetCommentError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                getComments = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "An error occurred while fetching all comments."
                                )
                            )
                        }
                    }
                }
            }
    }

}