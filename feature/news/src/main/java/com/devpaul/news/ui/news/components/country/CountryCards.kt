package com.devpaul.news.ui.news.components.country

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType

@Composable
fun CountryCards(
    countryState: ResultState<CountryEntity>,
    selectedCountry: CountryItemEntity?,
    onCountrySelected: (CountryItemEntity) -> Unit,
) {
    when (countryState) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.COUNTRY_CARD)
        }

        is ResultState.Success -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())

                ) {
                    countryState.response.data.forEach { countryItem ->
                        CountryCard(
                            country = countryItem,
                            isSelected = selectedCountry?.id == countryItem.id,
                            onClick = {
                                onCountrySelected(countryItem)
                            }
                        )
                    }
                }
            }
        }

        is ResultState.Error -> {
            Text(
                text = countryState.message,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {
            // Handle other states if necessary
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryCardsPreview() {
    val selectedCountry by remember { mutableStateOf<CountryItemEntity?>(null) }
    CountryCards(
        countryState = ResultState.Success(NewsMock().countryMock),
        selectedCountry = selectedCountry,
        onCountrySelected = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CountryCardsLoadingPreview() {
    CountryCards(
        countryState = ResultState.Loading,
        selectedCountry = null,
        onCountrySelected = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CountryCardsErrorPreview() {
    CountryCards(
        countryState = ResultState.Error("Failed to load countries"),
        selectedCountry = null,
        onCountrySelected = {}
    )
}