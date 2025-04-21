package com.devpaul.infoxperu.feature.home.news.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.ListingData
import com.devpaul.infoxperu.domain.models.res.PostData
import com.devpaul.infoxperu.domain.models.res.PostDataWrapper
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.feature.Screen
import com.devpaul.infoxperu.feature.util.ui.news_screen.RedditCard
import com.devpaul.infoxperu.feature.util.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.feature.util.ui.news_screen.errors.NoNewsAvailableCard
import com.devpaul.infoxperu.feature.util.ui.skeleton.RedditSkeleton
import com.google.gson.Gson

@Composable
fun RedditCards(
    navController: NavController,
    selectedCountry: Country,
    redditState: ResultState<RedditResponse>,
    context: Context
) {
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(redditState) {
        showSkeleton = redditState is ResultState.Loading
    }

    if (showSkeleton) {
        RedditSkeleton()
    } else {
        RedditContent(
            navController = navController,
            selectedCountry = selectedCountry,
            redditState = redditState,
            context = context
        )
    }
}

@Composable
fun RedditContent(
    navController: NavController,
    selectedCountry: Country,
    redditState: ResultState<RedditResponse>?,
    context: Context
) {

    when (redditState) {
        is ResultState.Loading -> {
            RedditSkeleton()
        }

        is ResultState.Success -> {
            if (redditState.data.data.children.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Reddit Posts",
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        val gson = Gson()
                        val countryJson = Uri.encode(gson.toJson(selectedCountry))
                        Text(
                            text = "Ver Más",
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Blue,
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    Screen.AllNews.createRoute(
                                        "reddit",
                                        countryJson
                                    )
                                )
                            }
                        )
                    }

                    LazyRow(
                        modifier = Modifier
                    ) {
                        items(redditState.data.data.children) { redditItems ->
                            RedditCard(redditChildren = redditItems, context = context)
                        }
                    }

                    Text(
                        text = "Cantidad: ${redditState.data.data.children.size}",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 8.dp, end = 8.dp),
                    )
                }
            } else {
                NoNewsAvailableCard()
            }
        }

        else -> {
            ErrorCard()
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun PreviewRedditLoading() {
    RedditCards(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        redditState = ResultState.Loading,
        context = LocalContext.current
    )
}

@Preview(showBackground = true, name = "Success State")
@Composable
fun PreviewRedditSuccess() {
    val samplePostData = PostData(subreddit = "news", title = "Sample News Title")
    val postDataWrapper = PostDataWrapper(kind = "Listing", data = samplePostData)
    val listingData = ListingData(children = listOf(postDataWrapper))
    val redditResponse = RedditResponse(kind = "Listing", data = listingData)
    RedditCards(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        redditState = ResultState.Success(redditResponse),
        context = LocalContext.current
    )
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun PreviewRedditError() {
    RedditCards(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        redditState = ResultState.Error(Exception("Network Error")),
        context = LocalContext.current
    )
}
