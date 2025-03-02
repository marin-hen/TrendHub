package com.example.trending.data

import com.example.core.network.ApiExecutor
import com.example.core.network.ApiManager
import com.example.trending.data.models.toDomain
import com.example.trending.data.source.TrendingApi
import com.example.trending.domain.models.GetRepositoriesParams
import com.example.trending.domain.models.GetRepositoryByNameAndOwnerParams
import com.example.trending.domain.models.TrendingRepository
import com.example.trending.domain.usecase.TrendRepository
import javax.inject.Inject

internal class TrendingRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager
) : TrendingApi by apiManager.create<TrendingApi>(), ApiExecutor by apiManager, TrendRepository {
    override suspend fun getTrendingRepositories(param: GetRepositoriesParams) =
        apiManager.execute {
            getRepositories(
                query = "language:java created:>2025-02-04",
                perPage = param.numResultsPerPage,
                page = param.page
            ).toDomain()
        }

    override suspend fun getTrendingRepository(param: GetRepositoryByNameAndOwnerParams): TrendingRepository =
        apiManager.execute {
            getRepositoryByNameAndOwner(name = param.name, owner = param.owner).toDomain()
        }
}