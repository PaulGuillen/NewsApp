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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.PinkGray
import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerBrush

class SectionBannerSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        SectionBannerSkeletonContent(modifier)
    }
}

@Composable
fun SectionBannerSkeletonContent(modifier: Modifier = Modifier) {
    val shimmerBrush = getShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PinkGray)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(brush = shimmerBrush, shape = RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(brush = shimmerBrush, shape = RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(12.dp)
                            .background(brush = shimmerBrush, shape = RoundedCornerShape(4.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(1.5.dp)
                .background(brush = shimmerBrush, shape = RoundedCornerShape(50))
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .padding(8.dp)
                .width(180.dp)
                .height(24.dp)
                .background(brush = shimmerBrush, shape = RoundedCornerShape(50))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SectionBannerSkeletonPreview() {
    SectionBannerSkeletonContent()
}