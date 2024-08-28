package com.devpaul.infoxperu.feature.home.home_view.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.feature.home.home_view.repository.GoogleNewsMapper
import com.devpaul.infoxperu.feature.home.home_view.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoogleNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val mapper: GoogleNewsMapper
) {
    suspend operator fun invoke(query: String, language: String): ResultState<GoogleNewsJSON> {
        return try {
            withContext(Dispatchers.IO) {
                val response = repository.googleNews(query, language)
                val mappedResponse = mapper.mapToGoogleNewsJSON(response)
                ResultState.Success(mappedResponse)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }
}
