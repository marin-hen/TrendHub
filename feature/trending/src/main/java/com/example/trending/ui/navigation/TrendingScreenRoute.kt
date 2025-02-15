package com.example.trending.ui.navigation

import com.example.core.navigation.BaseRoute
import kotlinx.serialization.Serializable

@Serializable
data object TrendingScreenRoute : BaseRoute

@Serializable
data class RepoDetailsScreenRoute(val repoId: String) : BaseRoute