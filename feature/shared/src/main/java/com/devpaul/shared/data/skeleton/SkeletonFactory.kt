package com.devpaul.shared.data.skeleton

import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.skeleton.profile.ProfileSkeleton

object SkeletonFactory {
    fun get(type: SkeletonType): SkeletonStrategy = when (type) {
        SkeletonType.PROFILE_ACCOUNT -> ProfileSkeleton()
        SkeletonType.OTHER -> ProfileSkeleton()
    }
}