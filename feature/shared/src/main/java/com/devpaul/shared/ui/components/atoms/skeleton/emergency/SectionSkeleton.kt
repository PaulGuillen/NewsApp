package com.devpaul.shared.ui.components.atoms.skeleton.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerBrush

class SectionSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        SectionSkeletonContent(modifier)
    }
}

@Composable
fun SectionSkeletonContent(modifier: Modifier = Modifier) {
    val shimmerBrush = getShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush = shimmerBrush,
                        shape = RoundedCornerShape(20.dp)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencySkeletonPreview() {
    SectionSkeletonContent()
}
