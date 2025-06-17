package com.devpaul.shared.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.core_platform.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    title: String,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    var isSearching by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(
                top = if (isSearching) 16.dp else 0.dp,
            )
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(0.dp)),
        color = White
    ) {
        if (!isSearching) {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                actions = {
                    IconButton(onClick = { isSearching = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "Buscar"
                        )
                    }
                }
            )
        } else {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 8.dp),
                placeholder = { Text("Buscar distrito...") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Buscar"
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "Cerrar búsqueda",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable {
                                isSearching = false
                                onSearchQueryChanged("")
                            }
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrickRed,
                    cursorColor = BrickRed,
                    focusedLabelColor = BrickRed
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarWithSearchPreview() {
    TopBarWithSearch(
        title = "InfoXPeru",
        searchQuery = "",
        onSearchQueryChanged = {}
    )
}