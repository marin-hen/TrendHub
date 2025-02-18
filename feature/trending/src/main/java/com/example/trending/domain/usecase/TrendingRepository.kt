package com.example.trending.domain.usecase

import com.example.trending.domain.models.TrendingRepo

interface TrendingRepository {
    suspend fun getTrendingRepositories(page: Int, perPage: Int): List<TrendingRepo>
}