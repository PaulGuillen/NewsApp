package com.devpaul.home.ui.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.data.datasource.mock.DollarQuoteMock
import com.devpaul.home.data.datasource.mock.UITMock
import com.devpaul.home.ui.home.components.DollarQuoteCard
import com.devpaul.home.ui.home.components.SectionBanner
import com.devpaul.home.ui.home.components.UITCard
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.molecules.TopBarPrincipal
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val context = LocalContext.current

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = true,
            header = {
                TopBarPrincipal(
                    style = 1,
                    onEndIconClick = {
                        navController.navigate(
                            route = Screen.Acknowledgment.route
                        )
                    },
                )
            },
            body = {
                HomeBody(
                    navController = navController,
                    context = context,
                    uiState = uiState,
                    onIntent = onIntent,
                )
            },
            footer = {
                BottomNavigationBar(navController)
            },
        )
    }
}

@Composable
fun HomeBody(
    navController: NavHostController,
    context: Context,
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onIntent: (HomeUiIntent) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionBanner(
            navHostController = navController,
            sectionState = uiState.section,
            onRetryClick = {
                onIntent(HomeUiIntent.GetSections)
            }
        )

        HorizontalDivider(thickness = 1.5.dp, color = Color.LightGray)

        Text(
            text = "Información diaria de Perú",
            fontSize = 14.sp,
            modifier = modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )

        HorizontalDivider(thickness = 1.5.dp, color = Color.LightGray)

        DollarQuoteCard(
            context = context,
            dollarQuoteState = uiState.dollarQuote,
            onRetry = {
                onIntent(HomeUiIntent.GetDollarQuote)
            }
        )

        UITCard(
            context = context,
            uitState = uiState.uitValue,
            onRetry = {
                onIntent(HomeUiIntent.GetUITValue)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    BaseContentLayout(
        isBodyScrollable = false,
        header = {
            TopBar(
                title = "Home",
            )
        },
        body = {
            HomeBody(
                navController = navController,
                context = LocalContext.current,
                uiState = HomeUiState(
                    dollarQuote = ResultState.Success(DollarQuoteMock().dollarQuoteMock),
                    uitValue = ResultState.Success(UITMock().uitMock),
                ),
                onIntent = {}
            )
        },
        footer = {
            BottomNavigationBar(navController)
        }
    )
}