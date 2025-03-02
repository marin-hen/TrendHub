package com.example.trending.ui.details

import androidx.annotation.DrawableRes
import com.example.core.designsystem.theme.Localizer
import com.example.core.designsystem.ui.ScreenResources
import com.example.trending.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RepositoryDetailsResources @Inject constructor() : ScreenResources {
    @DrawableRes
    val starIcon: Int = R.drawable.ic_star
    @DrawableRes
    val circleIcon: Int = R.drawable.ic_circle
    @DrawableRes
    val forkIcon: Int = R.drawable.ic_fork
    @DrawableRes
    val watchersIcon: Int = R.drawable.ic_watchers
    @DrawableRes
    val openIssuesIcon: Int = R.drawable.ic_open_issues

    val noDescription: Localizer = Localizer.Res(R.string.no_description)
    val license: Localizer = Localizer.Res(R.string.license)
    val language: Localizer = Localizer.Res(R.string.language)
    val updatedAt: Localizer = Localizer.Res(R.string.updated_at)
    val createdAt: Localizer = Localizer.Res(R.string.created_at)
}