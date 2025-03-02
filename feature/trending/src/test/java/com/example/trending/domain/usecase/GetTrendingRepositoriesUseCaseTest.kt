package com.example.trending.domain.usecase

import com.example.core.models.BusinessException
import com.example.core.usecase.ExceptionsResolver
import com.example.trending.domain.models.GetRepositoriesParams
import com.example.trending.domain.models.TrendingResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetTrendingRepositoriesUseCaseTest {

    private lateinit var repository: TrendRepository
    private lateinit var useCase: GetTrendingRepositoriesUseCase

    private val exceptionsResolver = mockk<ExceptionsResolver> {
        every { resolveException(any()) } answers { arg<Throwable>(0) as BusinessException }
    }

    @BeforeEach
    fun setUp() {
        repository = mockk()
        val testDispatcher = StandardTestDispatcher()
        useCase = GetTrendingRepositoriesUseCase(testDispatcher, exceptionsResolver, repository)
    }

    @Test
    fun `execute returns trending response on success`() = runTest {
        val params = GetRepositoriesParams(
            page = 1,
            numResultsPerPage = 10
        )
        val expectedResponse = TrendingResponse(
            items = listOf(),
            totalCount = 10
        )
        coEvery { repository.getTrendingRepositories(params) } returns expectedResponse
        val result = useCase.execute(params)
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `execute throws exception on failure`() = runTest {
        val params = GetRepositoriesParams(
            page = 1,
            numResultsPerPage = 10
        )
        val exception = Exception("Network error")
        coEvery { repository.getTrendingRepositories(params) } throws exception

        assertThrows(Exception::class.java) {
            runTest {
                useCase.execute(params)
            }
        }
    }
}
