package com.devpaul.shared.data.skeleton

import com.devpaul.shared.data.repository.SkeletonStrategy
import com.devpaul.shared.ui.components.atoms.skeleton.emergency.SectionSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.emergency.SectionSkeletonContent
import com.devpaul.shared.ui.components.atoms.skeleton.home.AcknowledgmentSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.home.DollarQuoteSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.home.SectionBannerSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.home.UITCardSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.news.CountryCardSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.news.GeneralNewSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.news.NewsDetailSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.profile.GetCommentSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.profile.PostScreenSkeleton
import com.devpaul.shared.ui.components.atoms.skeleton.profile.ProfileSkeleton

object SkeletonFactory {
    fun get(type: SkeletonType): SkeletonStrategy = when (type) {
        SkeletonType.PROFILE_ACCOUNT -> ProfileSkeleton()
        SkeletonType.SECTION_BANNER -> SectionBannerSkeleton()
        SkeletonType.DOLLAR_QUOTE -> DollarQuoteSkeleton()
        SkeletonType.UIT_CARD -> UITCardSkeleton()
        SkeletonType.ACKNOWLEDGEMENT -> AcknowledgmentSkeleton()
        SkeletonType.COUNTRY_CARD -> CountryCardSkeleton()
        SkeletonType.GENERAL_NEWS -> GeneralNewSkeleton()
        SkeletonType.NEWS_DETAIL -> NewsDetailSkeleton()
        SkeletonType.POST_SCREEN -> PostScreenSkeleton()
        SkeletonType.GET_COMMENT -> GetCommentSkeleton()
        SkeletonType.SECTION_EMERGENCY -> SectionSkeleton()
        SkeletonType.OTHER -> ProfileSkeleton()
    }
}