package com.example.trending.ui.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.core.designsystem.theme.ThemeProvider.dimens
import com.example.core.designsystem.theme.ThemeProvider.typography
import com.example.core.designsystem.ui.currentResources
import com.example.core.designsystem.utils.toDisplayString
import com.example.core.ui.LocalSkeleton
import com.example.core.ui.Screen
import com.example.core.ui.Skeleton
import com.example.core.ui.TrendHubPreview
import com.example.core.ui.shimmerEffect
import com.example.trending.domain.models.TrendingRepository
import com.example.trending.ui.details.DetailsViewModel.DetailsAction
import java.time.Instant

@Composable
fun DetailsScreen() {
    Screen(viewModel = hiltViewModel<DetailsViewModel>()) {
        DetailsScreenContent(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreenContent(state: DetailsState) {
    CompositionLocalProvider(LocalSkeleton provides Skeleton(state.isLoading)) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = state.repository?.fullName ?: "",
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimens.padding16),
        verticalArrangement = Arrangement.spacedBy(dimens.size16)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.size12)
        ) {

            Box(
                modifier = Modifier
                    .size(dimens.size64)
                    .clip(CircleShape)
                    .shimmerEffect()
            )

            Box(
                modifier = Modifier
                    .height(dimens.size24)
                    .clip(RoundedCornerShape(dimens.size12))
                    .fillMaxWidth(0.5f)
                    .shimmerEffect()
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(dimens.size12))
                .fillMaxWidth()
                .height(dimens.size48)
                .shimmerEffect()
        )

        repeat(2) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimens.size12))
                    .fillMaxWidth()
                    .height(dimens.size24)
                    .shimmerEffect()
            )
        }
    }
}

@Composable
private fun ScreenContent(state: DetailsState) {
    state.repository?.let {
        RepositoryDetailScreen(state.repository)
    }
}

@Composable
fun RepositoryDetailScreen(repository: TrendingRepository) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimens.padding16),
        verticalArrangement = Arrangement.spacedBy(dimens.size16)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OwnerAvatar(
                avatarUrl = repository.ownerAvatarUrl,
                modifier = Modifier.size(dimens.size64)
            )
            Spacer(modifier = Modifier.width(dimens.size12))
            Text(
                text = repository.ownerName,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        RepositoryDescription(description = repository.description)
        RepositoryStats(repo = repository)
        RepositoryMetadata(repository = repository)
    }
}

@Composable
private fun OwnerAvatar(
    avatarUrl: String,
    modifier: Modifier = Modifier
) {

    AsyncImage(
        model = avatarUrl,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .border(dimens.size2, MaterialTheme.colorScheme.primary, CircleShape)
    )
}

@Composable
private fun RepositoryDescription(description: String?) {
    val res = currentResources<RepositoryDetailsResources>()
    val displayText = if (description.isNullOrBlank()) res.noDescription.value() else description
    Text(
        text = displayText,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontStyle = if (description.isNullOrBlank()) FontStyle.Italic else FontStyle.Normal
        ),
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun RepositoryStats(repo: TrendingRepository) {
    val res = currentResources<RepositoryDetailsResources>()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.size16)
    ) {
        StatItem(
            iconRes = res.starIcon,
            count = repo.stars
        )
        StatItem(
            iconRes = res.forkIcon,
            count = repo.forks
        )
        StatItem(
            iconRes = res.openIssuesIcon,
            count = repo.openIssuesCount
        )
        StatItem(
            iconRes = res.watchersIcon,
            count = repo.watchers
        )
    }
}

@Composable
private fun StatItem(iconRes: Int, count: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(dimens.size24)
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun RepositoryMetadata(repository: TrendingRepository) {
    val res = currentResources<RepositoryDetailsResources>()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.size2)
    ) {
        Text(
            text = res.language.localizerValue(repository.language).value(),
            style = MaterialTheme.typography.bodySmall
        )
        repository.license?.let { license ->
            Text(
                text = res.license.localizerValue(license).value(),
                style = typography.bodySmall
            )
        }
        Text(
            text = res.createdAt.localizerValue(repository.createdAt.toDisplayString()).value(),
            style = typography.bodySmall
        )
        Text(
            text = res.updatedAt.localizerValue(repository.updatedAt.toDisplayString()).value(),
            style = typography.bodySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RepositoryDetailScreenPreview() {
    val repository = TrendingRepository(
        id = 1,
        name = "deepseek4j",
        fullName = "pig-mesh/deepseek4j",
        description = "A simple screen parsing tool towards pure vision based GUI agent",
        language = "Python",
        stars = 961,
        watchers = 212,
        forks = 589,
        ownerName = "FujiwaraChoki",
        ownerAvatarUrl = "https://avatars.githubusercontent.com/u/68727851?v=4",
        license = null,
        openIssuesCount = 0,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )


    DetailsScreenContentPreview(
        state = DetailsState(
            isLoading = false,
            uiError = null,
            repository = repository
        )
    )
}

@Preview
@ExperimentalMaterial3Api
@Composable
private fun DetailsScreenContentPreview(state: DetailsState = DetailsState()) {
    TrendHubPreview<DetailsState, DetailsAction>(
        resources = RepositoryDetailsResources(),
        state = state
    ) {
        DetailsScreenContent(it)
    }
}