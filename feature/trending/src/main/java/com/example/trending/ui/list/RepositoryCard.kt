package com.example.trending.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.designsystem.ThemeProvider.dimens
import com.example.core.designsystem.ThemeProvider.typography
import com.example.core.designsystem.ui.currentResources
import com.example.core.ui.TrendHubPreview
import com.example.core.ui.shimmerEffect
import com.example.trending.domain.models.TrendingRepo
import com.example.trending.ui.list.TrendingViewModel.TrendingAction

private const val DEFAULT_MAX_LINES = 1

@Composable
fun RepositoryCard(
    modifier: Modifier = Modifier,
    repo: TrendingRepo,
    onClick: () -> Unit,
) {
    val res = currentResources<TrendingResources>()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.size12))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.size12)
                .fillMaxWidth()
        ) {
            RepositoryTitle(repo.fullName)
            RepositoryDescription(repo.description, res)
            Spacer(modifier = Modifier.height(dimens.size10))
            RepositoryStats(repo, res)
        }
    }
}

@Composable
private fun RepositoryTitle(title: String) {
    Text(
        text = title,
        style = typography.headlineMedium,
        maxLines = DEFAULT_MAX_LINES,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun RepositoryDescription(description: String, res: TrendingResources) {
    val isDescriptionEmpty = description.isEmpty()
    Text(
        text = if (isDescriptionEmpty) res.noDescription.value() else description,
        style = typography.bodyMedium.copy(
            fontStyle = if (isDescriptionEmpty) FontStyle.Italic else FontStyle.Normal
        ),
        maxLines = DEFAULT_MAX_LINES,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun RepositoryStats(repo: TrendingRepo, res: TrendingResources) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.size12)
    ) {
        RepoStatItem(iconRes = res.circleIcon, value = repo.language)
        RepoStatItem(iconRes = res.starIcon, value = repo.stars.toString())
        RepoStatItem(iconRes = res.forkIcon, value = repo.forks.toString())
    }
}

@Composable
private fun RepoStatItem(iconRes: Int, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(dimens.size24),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(dimens.size2))
        Text(
            text = value,
            style = typography.bodySmall
        )
    }
}

@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(dimens.size12))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
        )
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun RepositoryCardPreview(state: TrendingState = TrendingState()) {
    TrendHubPreview<TrendingState, TrendingAction>(
        resources = TrendingResources(),
        state = state
    ) {
        RepositoryCard(
            repo = TrendingRepo(
                id = 1,
                fullName = "pig-mesh/deepseek4j",
                description = "A simple screen parsing tool towards pure vision based GUI agent",
                language = "Python",
                stars = 961,
                watchers = 212,
                forks = 589,
                ownerName = "FujiwaraChoki",
                ownerAvatarUrl = "https://avatars.githubusercontent.com/u/68727851?v=4"
            ),
            onClick = {},
            modifier = Modifier
        )
    }
}
