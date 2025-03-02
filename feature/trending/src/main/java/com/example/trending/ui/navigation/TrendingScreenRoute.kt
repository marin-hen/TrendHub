package com.example.trending.ui.navigation

import com.example.core.navigation.BaseRoute
import kotlinx.serialization.Serializable

@Serializable
data object TrendingScreenRoute : BaseRoute

@Serializable
data class RepoDetailsScreenRoute(val owner: String, val name: String) : BaseRoute