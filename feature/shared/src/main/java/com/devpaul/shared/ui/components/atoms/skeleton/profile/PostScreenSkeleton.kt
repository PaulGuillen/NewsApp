package com.devpaul.shared.ui.components.atoms.skeleton.profile

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerBrush

class PostScreenSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        PostScreenSkeletonContent(modifier)
    }
}

@Composable
fun PostScreenSkeletonContent(modifier: Modifier = Modifier) {
    val shimmerBrush = getShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = shimmerBrush,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(34.dp)
                    .height(30.dp)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(4.dp))
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenSkeletonPreview() {
    PostScreenSkeletonContent()
}