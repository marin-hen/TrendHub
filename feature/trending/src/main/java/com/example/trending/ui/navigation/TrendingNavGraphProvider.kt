package com.example.trending.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.NavGraphProvider
import com.example.trending.ui.details.DetailsScreen
import com.example.trending.ui.list.TrendingScreen
import javax.inject.Inject

internal class TrendingNavGraphProvider @Inject constructor() : NavGraphProvider {
    override fun provideGraph(graphBuilder: NavGraphBuilder) = with(graphBuilder) {

        composable<TrendingScreenRoute> {
            TrendingScreen()
        }

        composable<RepoDetailsScreenRoute> {
            DetailsScreen()
        }
    }
}