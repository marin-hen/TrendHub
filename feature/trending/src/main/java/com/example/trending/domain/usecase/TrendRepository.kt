package com.example.trending.domain.usecase

import com.example.trending.domain.models.GetRepositoriesParams
import com.example.trending.domain.models.GetRepositoryByNameAndOwnerParams
import com.example.trending.domain.models.TrendingRepository
import com.example.trending.domain.models.TrendingResponse

interface TrendRepository {
    suspend fun getTrendingRepositories(param: GetRepositoriesParams): TrendingResponse
    suspend fun getTrendingRepository(param: GetRepositoryByNameAndOwnerParams): TrendingRepository
}