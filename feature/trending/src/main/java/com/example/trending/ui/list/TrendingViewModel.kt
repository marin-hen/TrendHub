package com.example.trending.ui.list

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
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

@Immutable
data class TrendingState(
    val repositories: ImmutableList<TrendingRepo> = persistentListOf(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val uiError: UiError? = null,
) : ViewState {
    override fun uiError(): UiError? = uiError
    fun loading() = copy(isLoading = true, uiError = null)
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
                update { state ->
                    state.loading()
                }

            useCase(10).onSuccess { response ->
                update {
                    it.copy(
                        isLoading = false,
                        repositories = response.toImmutableList()
                    )
                }
            }
        }
    }

    override suspend fun handleActions(action: TrendingAction) {
    }

    sealed interface TrendingAction : ViewAction
}
