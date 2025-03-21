package com.example.trending.ui.list

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import com.example.core.datastore.DataStoreKeys.KEY_DARK_THEME
import com.example.core.datastore.DataStoreManager
import com.example.core.designsystem.UiError
import com.example.core.designsystem.ViewState
import com.example.core.designsystem.theme.Localizer
import com.example.core.models.ApiRateLimitExceededException
import com.example.core.ui.BaseViewModelWithResources
import com.example.core.ui.ViewAction
import com.example.core.ui.asAlert
import com.example.trending.domain.models.GetRepositoriesParams
import com.example.trending.domain.models.TrendingRepository
import com.example.trending.domain.usecase.GetTrendingRepositoriesUseCase
import com.example.trending.ui.delegates.TrendingNavigationDelegate
import com.example.trending.ui.list.TrendingViewModel.TrendingAction.LoadMoreRepositories
import com.example.trending.ui.list.TrendingViewModel.TrendingAction.NavigateToDetails
import com.example.trending.ui.list.TrendingViewModel.TrendingAction.RefreshRepositories
import com.example.trending.ui.list.TrendingViewModel.TrendingAction.OnChangeThemeClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

@Immutable
data class TrendingState(
    val repositories: ImmutableList<TrendingRepository> = persistentListOf(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false,
    val uiError: UiError? = null,
) : ViewState {
    override fun uiError(): UiError? = uiError
    fun loading() = copy(isLoading = true, uiError = null)
    fun refreshing() = copy(
        isRefreshing = true,
        uiError = null,
        isLoading = true,
        repositories = persistentListOf()
    )

    fun failure(uiError: UiError?) =
        copy(isLoading = false, isRefreshing = false, uiError = uiError)
}

@HiltViewModel
internal class TrendingViewModel @Inject constructor(
    resources: TrendingResources,
    savedStateHandle: SavedStateHandle,
    val getTrendingRepositoriesUseCase: GetTrendingRepositoriesUseCase,
    private val navDelegate: TrendingNavigationDelegate,
    private val dataStoreManager: DataStoreManager,
) : BaseViewModelWithResources<TrendingState, TrendingViewModel.TrendingAction, TrendingResources>(
    initialState = { TrendingState() },
    savedStateHandle = savedStateHandle,
    resources = resources
) {

    private var currentPage = INITIAL_PAGE

    init {
        launch {
            update { it.loading() }
            loadThemePreference()
            loadRepositories()
        }
    }

    private suspend fun loadThemePreference() {
        val savedTheme = dataStoreManager.getBoolean(KEY_DARK_THEME, defaultValue = false)
        update { state -> state.copy(isDarkTheme = savedTheme) }
    }

    private suspend fun loadRepositories() {
        val params = GetRepositoriesParams(
            numResultsPerPage = PAGE_SIZE,
            page = currentPage,
        )
        getTrendingRepositoriesUseCase(params).onSuccess { response ->
            update { state ->
                state.copy(
                    isLoading = false,
                    isRefreshing = false,
                    repositories = (state.repositories + response.items).toPersistentList()
                )
            }
        }.onFailure { error ->

            when (error) {

                is ApiRateLimitExceededException -> {
                    update {
                        it.failure(
                            uiError = error.asAlert(
                                description = Localizer.Text(error.message)
                            )
                        )
                    }
                }

                else -> update {
                    it.failure(uiError = error.asAlert())
                }
            }
        }
    }

    private suspend fun loadMoreRepositories() {
        currentPage++
        loadRepositories()
    }

    private suspend fun changeTheme(isEnabled: Boolean) {
        update { state -> state.copy(isDarkTheme = isEnabled) }
        dataStoreManager.saveBoolean(KEY_DARK_THEME, isEnabled)
    }

    internal suspend fun refreshRepositories() {
        currentPage = INITIAL_PAGE
        update { it.refreshing() }
        loadRepositories()
    }

    override suspend fun handleActions(action: TrendingAction) {
        when (action) {
            is NavigateToDetails -> navDelegate.navigateToDetailsScreen(
                action.owner,
                action.name,
            )

            RefreshRepositories -> refreshRepositories()
            LoadMoreRepositories -> loadMoreRepositories()
            is OnChangeThemeClicked -> changeTheme(action.isEnabled)
        }
    }

    sealed interface TrendingAction : ViewAction {
        data class NavigateToDetails(val owner: String, val name: String) : TrendingAction
        data object LoadMoreRepositories : TrendingAction
        data object RefreshRepositories : TrendingAction
        data class OnChangeThemeClicked(val isEnabled: Boolean) : TrendingAction
    }

    private companion object {
        const val INITIAL_PAGE = 1
        const val PAGE_SIZE = 10
    }
}