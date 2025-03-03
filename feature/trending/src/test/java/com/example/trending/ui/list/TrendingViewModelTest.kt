package com.example.trending.ui.list

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.core.datastore.DataStoreManager
import com.example.trending.domain.models.GetRepositoriesParams
import com.example.trending.domain.models.TrendingRepository
import com.example.trending.domain.models.TrendingResponse
import com.example.trending.domain.usecase.GetTrendingRepositoriesUseCase
import com.example.trending.ui.delegates.TrendingNavigationDelegate
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class TrendingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getTrendingRepositoriesUseCase: GetTrendingRepositoriesUseCase
    private lateinit var trendingNavigationDelegate: TrendingNavigationDelegate
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var viewModel: TrendingViewModel

    private val dummyRepository = TrendingRepository(
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
    private val dummyResponse = TrendingResponse(
        items = listOf(dummyRepository), totalCount = 10
    )

    private fun createTrendingViewModel(): TrendingViewModel {
        val trendingResources: TrendingResources = mockk(relaxed = true)
        return TrendingViewModel(
            resources = trendingResources,
            savedStateHandle = SavedStateHandle(),
            getTrendingRepositoriesUseCase = getTrendingRepositoriesUseCase,
            navDelegate = trendingNavigationDelegate,
            dataStoreManager = dataStoreManager
        )
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getTrendingRepositoriesUseCase = mockk()
        trendingNavigationDelegate = mockk(relaxed = true)
        dataStoreManager = mockk(relaxed = true)

        viewModel = createTrendingViewModel()

        coEvery { getTrendingRepositoriesUseCase(any<GetRepositoriesParams>()) } returns Result.success(
            dummyResponse
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cancel()
    }

    @Test
    fun `initial state should be loading`() = testScope.runTest {
        val viewModel = createTrendingViewModel()

        viewModel.state.test {
            awaitItem()
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `load repositories success updates state`() = testScope.runTest {
        viewModel.refreshRepositories()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertFalse(state.isRefreshing)
            assertTrue(state.repositories.contains(dummyRepository))
            cancel()
        }
    }

    @Test
    fun `load repositories failure updates state with error`() = testScope.runTest {
        val exception = Exception("error")
        coEvery { getTrendingRepositoriesUseCase(any<GetRepositoriesParams>()) } returns Result.failure(
            exception
        )

        viewModel.refreshRepositories()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertFalse(state.isRefreshing)
            assertNotNull(state.uiError)
            cancel()
        }
    }

    @Test
    fun `handle NavigateToDetails triggers navigation delegate`() = testScope.runTest {
        val owner = "owner"
        val name = "repo"

        viewModel.handleActions(TrendingViewModel.TrendingAction.NavigateToDetails(owner, name))
        advanceUntilIdle()

        coVerify(exactly = 1) { trendingNavigationDelegate.navigateToDetailsScreen(owner, name) }
    }
}