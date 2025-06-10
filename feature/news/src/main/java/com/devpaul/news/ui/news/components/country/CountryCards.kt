package com.devpaul.news.ui.news.components.country

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.shared.ui.skeleton.CountryCardSkeleton

@Composable
fun CountryCards(
    country: CountryEntity?,
    countryError: String? = null,
    countryLoading: Boolean,
    selectedCountry: CountryItemEntity?,
    onCountrySelected: (CountryItemEntity) -> Unit,
) {

    if (countryLoading) {
        CountryCardSkeleton()
    } else {
        if (country != null) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                country.data.forEach { countryItem ->
                    CountryCard(
                        country = countryItem,
                        isSelected = selectedCountry?.id == countryItem.id,
                        onClick = {
                            onCountrySelected(countryItem)
                        }
                    )
                }
            }
        } else {
            Text(
                text = countryError ?: "No countries available",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}