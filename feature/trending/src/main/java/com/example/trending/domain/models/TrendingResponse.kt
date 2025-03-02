package com.example.trending.domain.models

data class TrendingResponse(
    val totalCount: Int,
    val items: List<TrendingRepository>
)