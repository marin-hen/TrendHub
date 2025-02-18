package com.example.trending.data

import com.example.core.network.ApiExecutor
import com.example.core.network.ApiManager
import com.example.trending.data.models.toDomain
import com.example.trending.data.source.TrendingApi
import com.example.trending.domain.models.TrendingRepo
import com.example.trending.domain.usecase.TrendingRepository
import javax.inject.Inject

internal class TrendingRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager
) : TrendingApi by apiManager.create<TrendingApi>(), ApiExecutor by apiManager, TrendingRepository {
    override suspend fun getTrendingRepositories(page: Int, perPage: Int): List<TrendingRepo> {
        return apiManager.execute {
            getTrendingRepositories(
                query = "language:java created:>2025-02-04",
                perPage = perPage,
                page = page
            ).items.map { it.toDomain() }
        }
    }
}