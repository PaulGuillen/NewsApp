package com.devpaul.profile.ui.suggestions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.profile.ui.suggestions.components.CommentCard
import com.devpaul.shared.ui.components.atoms.base.button.CustomButton
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestionsScreen(navController: NavHostController) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Nuevo comentario", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Escribe tu comentario") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                CustomButton(
                    text = "Enviar",
                    onClick = {
                        println("Comentario ingresado: $comment")
                        showSheet = false
                    },
                )
            }
        }
    }

    BaseContentLayout(
        applyBottomPaddingWhenNoFooter = true,
        header = { TopBar(title = "Comentarios") },
        body = { CommentsBody() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showSheet = true
                    scope.launch { sheetState.show() }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo comentario")
            }
        }
    )
}


@Composable
fun CommentsBody() {
    val comments = listOf(
        "Este artículo me pareció excelente.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
        "Buena información, gracias por compartir.",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        comments.forEach { comment ->
            CommentCard(
                name = "Juan Pérez",
                timeAgo = "2h ago",
                comment = comment,
                likes = 0
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestionsScreenPreview() {
    SuggestionsScreen(navController = rememberNavController())
}