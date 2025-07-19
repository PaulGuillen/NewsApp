package com.devpaul.shared.ui.components.atoms.skeleton.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devpaul.shared.data.repository.SkeletonStrategy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.devpaul.shared.ui.components.atoms.base.shimmer.getShimmerColor

class ProfileSkeleton : SkeletonStrategy {
    @Composable
    override fun Render(modifier: Modifier) {
        val shimmerColor = getShimmerColor()

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(shimmerColor)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(20.dp)
                    .background(shimmerColor, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(16.dp)
                    .background(shimmerColor, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp)
                    .background(shimmerColor, RoundedCornerShape(6.dp))
            )
        }
    }
}