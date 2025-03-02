package com.example.trending.ui.delegates

import com.example.core.navigation.Navigator
import com.example.trending.ui.navigation.RepoDetailsScreenRoute
import javax.inject.Inject

internal interface TrendingNavigationDelegate {
    fun navigateToDetailsScreen(name: String, owner: String)
}

internal class TrendingNavigationDelegateImpl @Inject constructor(
    private val navigator: Navigator
) : TrendingNavigationDelegate {
    override fun navigateToDetailsScreen(name: String, owner: String) {
        navigator.doAction {
            navigate(RepoDetailsScreenRoute(name, owner))
        }
    }
}