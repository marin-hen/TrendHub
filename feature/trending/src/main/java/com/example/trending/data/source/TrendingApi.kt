package com.example.trending.data.source

import com.example.trending.data.models.TrendingResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface TrendingApi {
    @GET("/search/repositories")
    suspend fun getTrendingRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): TrendingResponseDto
}