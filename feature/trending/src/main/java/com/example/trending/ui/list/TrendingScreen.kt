package com.example.trending.ui.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.designsystem.ThemeProvider.dimens
import com.example.core.designsystem.ui.currentResources
import com.example.core.ui.LocalSkeleton
import com.example.core.ui.PullToRefreshBox
import com.example.core.ui.Screen
import com.example.core.ui.Skeleton
import com.example.core.ui.TrendHubPreview
import com.example.core.ui.currentActionReceiver
import com.example.trending.domain.models.TrendingRepo
import com.example.trending.ui.list.TrendingViewModel.TrendingAction
import kotlinx.collections.immutable.toImmutableList

@Composable
fun TrendingScreen() {
    Screen(viewModel = hiltViewModel<TrendingViewModel>()) {
        TrendingScreenContent(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrendingScreenContent(state: TrendingState) {

    val action = currentActionReceiver<TrendingAction>()
    val res = currentResources<TrendingResources>()

    CompositionLocalProvider(LocalSkeleton provides Skeleton(state.isLoading)) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = res.title.value(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    modifier = Modifier.shadow(
                        elevation = dimens.elevation4
                    ),
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            content = { innerPadding ->
                AnimatedContent(
                    modifier = Modifier
                        .padding(innerPadding),
                    targetState = state.isLoading,
                ) { isLoading ->
                    if (isLoading) {
                        LoadingContent()
                    } else {
                        ScreenContent(state)
                    }
                }
            }
        )
    }
}

@Composable
private fun LoadingContent() {
    LazyColumn(
        contentPadding = PaddingValues(dimens.padding16),
        userScrollEnabled = false
    ) {
        items(15) {
            ShimmerCard(
                modifier = Modifier
                    .height(dimens.size112)
                    .padding(vertical = dimens.padding4)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(state: TrendingState) {
    val action = currentActionReceiver<TrendingAction>()
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { },
    ) {
        LazyColumn(
            contentPadding = PaddingValues(dimens.padding16)
        ) {
            items(state.repositories) { repositories ->
                RepositoryCard(
                    modifier = Modifier
                        .height(dimens.size112)
                        .padding(vertical = dimens.padding4),
                    repo = repositories,
                    onClick = { }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TrendingScreenPreview() {
    val repositories = List(10) { index ->
        TrendingRepo(
            id = index.toLong(),
            fullName = "pig-mesh/deepseek4j",
            description = "A simple screen parsing tool towards pure vision based GUI agent",
            language = "Python",
            stars = 961,
            watchers = 212,
            forks = 589,
            ownerName = "FujiwaraChoki",
            ownerAvatarUrl = "https://avatars.githubusercontent.com/u/68727851?v=4"
        )
    }

    TrendingScreenContentPreview(
        state = TrendingState(
            isLoading = false,
            isRefreshing = false,
            uiError = null,
            repositories = repositories.toImmutableList()
        )
    )
}

@Preview
@ExperimentalMaterial3Api
@Composable
private fun TrendingScreenContentPreview(state: TrendingState = TrendingState()) {
    TrendHubPreview<TrendingState, TrendingAction>(
        resources = TrendingResources(),
        state = state
    ) {
        TrendingScreenContent(it)
    }
}