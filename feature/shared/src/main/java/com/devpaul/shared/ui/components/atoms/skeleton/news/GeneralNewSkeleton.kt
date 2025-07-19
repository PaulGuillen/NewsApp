package com.devpaul.shared.ui.components.atoms.skeleton.news

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerBrush

class GeneralNewSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        GeneralNewSkeletonContent(modifier)
    }
}

@Composable
fun GeneralNewSkeletonContent(modifier: Modifier = Modifier) {
    val shimmerBrush = getShimmerBrush()

    Column(modifier = modifier.padding(16.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(20.dp)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(4.dp))
            )

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(20.dp)
                    .background(brush = shimmerBrush, shape = RoundedCornerShape(4.dp))
            )
        }

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            repeat(4) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(180.dp)
                        .padding(end = 18.dp)
                        .background(brush = shimmerBrush, shape = RoundedCornerShape(8.dp))
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp)
                .width(90.dp)
                .height(20.dp)
                .background(brush = shimmerBrush, shape = RoundedCornerShape(4.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GeneralNewSkeletonContentPreview() {
    GeneralNewSkeletonContent()
}