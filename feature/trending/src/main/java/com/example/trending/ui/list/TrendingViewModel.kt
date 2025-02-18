package com.example.trending.ui.list

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import com.example.core.designsystem.UiError
import com.example.core.designsystem.ViewState
import com.example.core.ui.BaseViewModelWithResources
import com.example.core.ui.ViewAction
import com.example.trending.domain.models.TrendingRepo
import com.example.trending.domain.usecase.GetTrendingRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@Immutable
data class TrendingState(
    val repositories: ImmutableList<TrendingRepo> = persistentListOf(),
    val isLoading: Boolean = false,
    val uiError: UiError? = null,
) : ViewState {
    override fun uiError(): UiError? = uiError
}

@HiltViewModel
internal class TrendingViewModel @Inject constructor(
    resources: TrendingResources,
    savedStateHandle: SavedStateHandle,
    val useCase: GetTrendingRepositoriesUseCase
) : BaseViewModelWithResources<TrendingState, TrendingViewModel.TrendingAction, TrendingResources>(
    initialState = { TrendingState() },
    savedStateHandle = savedStateHandle,
    resources = resources
) {

    init {
        launch {
            useCase(1).getOrNull().also {
                Log.d("KMary1", "response: ${it?.joinToString()} ")
            }
        }
    }

    override suspend fun handleActions(action: TrendingAction) {
    }

    sealed interface TrendingAction : ViewAction
}
