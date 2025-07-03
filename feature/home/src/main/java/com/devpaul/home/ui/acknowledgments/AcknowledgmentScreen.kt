package com.devpaul.home.ui.acknowledgments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BlueDark
import com.devpaul.home.data.datasource.mock.GratitudeMock
import com.devpaul.home.ui.home.components.AcknowledgmentSection
import com.devpaul.shared.domain.only
import com.devpaul.shared.ui.components.molecules.TopBarPrincipal
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AcknowledgmentScreen(navHostController: NavHostController) {

    val viewModel: AcknowledgmentViewModel = koinViewModel()
    val context = LocalContext.current

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
    ) { _, uiState, onIntent, _, _ ->
        Scaffold(
            topBar = {
                TopBarPrincipal(
                    style = 2,
                    onStartIconClick = { navHostController.popBackStack() },
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding.only(top = true)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .background(BlueDark)
                )

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .offset(y = (-60).dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Gracias por el contenido",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Agradecemos a las siguientes fuentes por proporcionar información confiable.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    AcknowledgmentSection(
                        context = context,
                        gratitudeState = uiState.gratitude,
                        onRetry = {
                            onIntent(AcknowledgmentUiIntent.GetGratitude)
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBaseContentLayout() {
    BaseContentLayout(
        isBodyScrollable = false,
        header = {
            TopBarPrincipal(
                style = 2,
                onStartIconClick = {}
            )
        },
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .background(BlueDark)
                )

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .offset(y = (-50).dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Gracias por el contenido",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Agradecemos a las siguientes fuentes por proporcionar información confiable.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    AcknowledgmentSection(
                        context = LocalContext.current,
                        gratitudeState = ResultState.Success(GratitudeMock().gratitudeMock),
                        onRetry = {
                            // Retry logic can be implemented here
                        },
                    )
                }
            }
        },
        footer = {}
    )
}