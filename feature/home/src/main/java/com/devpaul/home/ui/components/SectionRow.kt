package com.devpaul.home.ui.components

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BlueDark
import com.devpaul.core_platform.theme.White
import com.devpaul.home.domain.entity.SectionDataEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.shared.ui.skeleton.SectionsRowSkeleton

@Composable
fun SectionsRow(
    context: Context,
    sectionState: ResultState<SectionEntity>,
    onRetry: () -> Unit,
) {
    when (sectionState) {
        is ResultState.Loading -> {
            SectionsRowSkeleton()
        }

        is ResultState.Success -> {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                sectionState.response.data.forEach { sectionItem ->
                    ItemSection(context = context, sectionItem = sectionItem)
                }
            }
        }

        is ResultState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = sectionState.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black,
                            fontSize = 14.sp,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = onRetry,
                            shape = RectangleShape,
                            elevation = ButtonDefaults.buttonElevation(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlueDark,
                                contentColor = White,
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Reintentar", fontSize = 13.sp)
                        }
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun SectionsRowSuccessPreview() {
    SectionsRow(
        context = LocalContext.current,
        sectionState = ResultState.Success(
            SectionEntity(
                status = 200,
                message = "Success",
                data = SectionDataEntity(
                    id = "1",
                    title = "Section Title",
                    type = "type"
                ).let { listOf(it, it, it) }
            ),
        ),
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowLoadingPreview() {
    SectionsRow(
        context = LocalContext.current,
        sectionState = ResultState.Loading,
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowErrorPreview() {
    SectionsRow(
        context = LocalContext.current,
        sectionState = ResultState.Error(message = "Error loading sections"),
        onRetry = {}
    )
}