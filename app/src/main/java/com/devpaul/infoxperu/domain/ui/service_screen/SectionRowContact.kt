package com.devpaul.infoxperu.domain.ui.service_screen

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.domain.ui.skeleton.SectionsRowContactSkeleton

@Composable
fun SectionsRowContact(
    navHostController: NavHostController,
    sectionContactState: ResultState<List<Contact>>,
    context: Context,
) {
    SectionsRowContactContent(
        navHostController = navHostController,
        sectionContactState = sectionContactState,
        context = context,
    )
}

@Composable
fun SectionsRowContactContent(
    navHostController: NavHostController,
    sectionContactState: ResultState<List<Contact>>,
    context: Context,
) {
    when (sectionContactState) {
        is ResultState.Loading -> {
            SectionsRowContactSkeleton()
        }

        is ResultState.Success -> {
            if (sectionContactState.data.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                ) {
                    sectionContactState.data.forEach { sectionItem ->
                        ContactCard(navHostController, sectionItem)
                    }
                }
            } else {
                Text(text = "No hay secciones disponibles.")
            }
        }

        is ResultState.Error -> {
            ErrorCard(cardHeight = 140)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionsRowContactSuccessPreview() {
    val sectionsState = ResultState.Success(
        listOf(
            Contact("Noticias", "news", ""),
            Contact("Noticias", "news", ""),
        )
    )
    SectionsRowContactContent(
        navHostController = rememberNavController(),
        sectionContactState = sectionsState,
        context = LocalContext.current,
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowLoadingPreview() {
    SectionsRowContactContent(
        navHostController = rememberNavController(),
        sectionContactState = ResultState.Loading,
        context = LocalContext.current,
    )
}

@Preview(showBackground = true)
@Composable
fun SectionsRowErrorPreview() {
    val sectionsState = ResultState.Error(Exception("Failed to load data"))
    SectionsRowContactContent(
        navHostController = rememberNavController(),
        sectionContactState = sectionsState,
        context = LocalContext.current,
    )
}
