package com.example.trending.ui.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.core.designsystem.UiError
import com.example.core.designsystem.ViewState
import com.example.core.ui.BaseViewModelWithResources
import com.example.core.ui.ViewAction
import com.example.core.ui.asAlert
import com.example.trending.domain.models.GetRepositoryByNameAndOwnerParams
import com.example.trending.domain.models.TrendingRepository
import com.example.trending.domain.usecase.GetRepositoryByNameAndOwnerUseCase
import com.example.trending.ui.navigation.RepoDetailsScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Immutable
data class DetailsState(
    val repository: TrendingRepository? = null,
    val isLoading: Boolean = false,
    val uiError: UiError? = null,
) : ViewState {
    override fun uiError(): UiError? = uiError
    fun loading() = copy(isLoading = true, uiError = null)
    fun failure(uiError: UiError?) =
        copy(isLoading = false, uiError = uiError)
}

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    resources: RepositoryDetailsResources,
    savedStateHandle: SavedStateHandle,
    private val getRepositoryByNameAndOwnerUseCase: GetRepositoryByNameAndOwnerUseCase,
) : BaseViewModelWithResources<DetailsState, DetailsViewModel.DetailsAction, RepositoryDetailsResources>(
    initialState = { DetailsState() },
    savedStateHandle = savedStateHandle,
    resources = resources
) {

    init {
        launch {
            update { state ->
                state.loading()
            }
            loadDetails()
        }
    }

    private suspend fun loadDetails() {
        val route = savedStateHandle.toRoute<RepoDetailsScreenRoute>()
        val params = GetRepositoryByNameAndOwnerParams(
            name = route.name,
            owner = route.owner,
        )
        getRepositoryByNameAndOwnerUseCase.invoke(params).onSuccess { response ->
            update { state ->
                state.copy(
                    isLoading = false,
                    repository = response
                )
            }
        }.onFailure { error ->
            update { state ->
                state.failure(error.asAlert())
            }
        }
    }

    override suspend fun handleActions(action: DetailsAction) {}

    sealed interface DetailsAction : ViewAction
}