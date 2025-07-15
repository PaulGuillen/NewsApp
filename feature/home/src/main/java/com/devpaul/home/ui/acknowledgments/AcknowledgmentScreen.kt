package com.devpaul.home.ui.acknowledgments

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.PinkGray
import com.devpaul.home.data.datasource.mock.GratitudeMock
import com.devpaul.home.ui.home.components.AcknowledgmentSection
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
        AcknowledgmentContent(
            context = context,
            navHostController = navHostController,
            onIntent = onIntent,
            uiState = uiState,
        )
    }
}

@Composable
fun AcknowledgmentContent(
    context: Context,
    navHostController: NavHostController,
    onIntent: (AcknowledgmentUiIntent) -> Unit,
    uiState: AcknowledgmentUiState,
) {
    BaseContentLayout(
        applyBottomPaddingWhenNoFooter = false,
        isBodyScrollable = false,
        header = {
            TopBarPrincipal(
                style = 2,
                onStartIconClick = { navHostController.popBackStack() },
            )
        },
        body = {
            AcknowledgmentBody(
                context = context,
                uiState = uiState,
                onIntent = onIntent,
            )
        },
    )
}

@Composable
fun AcknowledgmentBody(
    context: Context,
    uiState: AcknowledgmentUiState,
    onIntent: (AcknowledgmentUiIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(PinkGray)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
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
                )
                Text(
                    text = "Agradecemos a las siguientes fuentes por proporcionar información confiable.",
                    fontSize = 14.sp,
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

@Preview(showBackground = true)
@Composable
fun AcknowledgmentContentPreview() {
    AcknowledgmentContent(
        context = LocalContext.current,
        navHostController = rememberNavController(),
        onIntent = {},
        uiState = AcknowledgmentUiState(
            gratitude = ResultState.Success(GratitudeMock().gratitudeMock)
        )
    )
}