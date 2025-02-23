package com.example.trending.ui.list

import androidx.annotation.DrawableRes
import com.example.core.designsystem.Localizer
import com.example.core.designsystem.ui.ScreenResources
import com.example.trending.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TrendingResources @Inject constructor() : ScreenResources {
    @DrawableRes
    val starIcon: Int = R.drawable.ic_star
    val circleIcon: Int = R.drawable.ic_circle
    val forkIcon: Int = R.drawable.ic_fork
    val title: Localizer = Localizer.Res(R.string.title)
    val noDescription: Localizer = Localizer.Res(R.string.no_description)
}