package com.devpaul.shared.data.skeleton

import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.skeleton.home.AcknowledgmentSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.home.DollarQuoteSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.home.SectionBannerSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.home.UITCardSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.profile.ProfileSkeleton

object SkeletonFactory {
    fun get(type: SkeletonType): SkeletonStrategy = when (type) {
        SkeletonType.PROFILE_ACCOUNT -> ProfileSkeleton()
        SkeletonType.SECTION_BANNER -> SectionBannerSkeleton()
        SkeletonType.DOLLAR_QUOTE -> DollarQuoteSkeleton()
        SkeletonType.UIT_CARD -> UITCardSkeleton()
        SkeletonType.ACKNOWLEDGEMENT -> AcknowledgmentSkeleton()
        SkeletonType.OTHER -> ProfileSkeleton()
    }
}