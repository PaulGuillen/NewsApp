package com.devpaul.shared.ui.components.atoms.skeleton.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerBrush

class UITCardSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        UITCardSkeletonContent(modifier)
    }
}

@Composable
fun UITCardSkeletonContent(modifier: Modifier = Modifier) {
    val shimmerBrush = getShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (it == 0) 0.7f else 0.5f)
                    .height(20.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .background(brush = shimmerBrush, shape = RoundedCornerShape(8.dp))
                .height(14.dp)
                .width(80.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .background(brush = shimmerBrush, shape = RoundedCornerShape(8.dp))
                .height(14.dp)
                .width(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SkeletonUITScreenPreview() {
    UITCardSkeletonContent()
}