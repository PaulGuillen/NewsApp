package com.devpaul.shared.ui.components.atoms.skeleton.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerBrush

class DollarQuoteSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        SkeletonDollarQuoteContent(modifier)
    }
}

@Composable
fun SkeletonDollarQuoteContent(modifier: Modifier = Modifier) {
    val shimmerBrush = getShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = shimmerBrush,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(18.dp)
                .align(Alignment.CenterHorizontally)
                .background(brush = shimmerBrush, shape = RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        repeat(2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(14.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(brush = shimmerBrush)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(12.dp)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(4.dp))
            )

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(12.dp)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(4.dp))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SkeletonDollarQuoteContentPreview() {
    SkeletonDollarQuoteContent()
}
