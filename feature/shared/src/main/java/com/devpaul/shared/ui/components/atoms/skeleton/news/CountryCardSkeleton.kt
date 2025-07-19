package com.devpaul.shared.ui.components.atoms.skeleton.news

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerBrush

class CountryCardSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        CountryCardSkeletonContent(modifier)
    }
}

@Composable
fun CountryCardSkeletonContent(modifier: Modifier = Modifier) {
    val shimmerBrush = getShimmerBrush()

    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(start = 10.dp, top = 2.dp, bottom = 2.dp, end = 2.dp)
    ) {
        repeat(5) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .padding(8.dp)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(8.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryCardSkeletonPreview() {
    CountryCardSkeletonContent()
}