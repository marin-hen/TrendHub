package com.example.trending.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.example.trending.domain.models.GetRepositoryByNameAndOwnerParams
import com.example.trending.domain.models.TrendingRepository
import com.example.trending.domain.usecase.GetRepositoryByNameAndOwnerUseCase
import com.example.trending.ui.navigation.RepoDetailsScreenRoute
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getRepositoryByNameAndOwnerUseCase: GetRepositoryByNameAndOwnerUseCase
    private lateinit var viewModel: DetailsViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var detailsResources: RepositoryDetailsResources

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

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        getRepositoryByNameAndOwnerUseCase = mockk()

        savedStateHandle = spyk(SavedStateHandle())
        detailsResources = mockk(relaxed = true)

        every { savedStateHandle.toRoute<RepoDetailsScreenRoute>() } returns RepoDetailsScreenRoute(
            name = "deepseek4j",
            owner = "pig-mesh"
        )

        coEvery { getRepositoryByNameAndOwnerUseCase.invoke(any<GetRepositoryByNameAndOwnerParams>()) } returns Result.success(dummyRepository)

        viewModel = DetailsViewModel(
            resources = detailsResources,
            savedStateHandle = savedStateHandle,
            getRepositoryByNameAndOwnerUseCase = getRepositoryByNameAndOwnerUseCase
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cancel()
    }

    @Test
    fun `final state should update with repository on success`() =
        testScope.runTest {
            viewModel.state.test {
                val finalState = awaitItem()
                assertFalse(finalState.isLoading)
                assertNotNull(finalState.repository)
                cancel()
            }
        }

    @Test
    fun `load details failure updates state with error`() = testScope.runTest {
        val exception = Exception("network error")
        coEvery { getRepositoryByNameAndOwnerUseCase.invoke(any<GetRepositoryByNameAndOwnerParams>()) } returns Result.failure(
            exception
        )

        viewModel = DetailsViewModel(
            resources = detailsResources,
            savedStateHandle = savedStateHandle,
            getRepositoryByNameAndOwnerUseCase = getRepositoryByNameAndOwnerUseCase
        )

        viewModel.state.test {
            awaitItem()
            awaitItem()
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertNotNull(errorState.uiError)
            cancel()
        }
    }
}