package com.example.trending.data.models

import com.example.trending.domain.models.TrendingResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TrendingResponseDto(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    @SerialName("items")
    val items: List<RepositoryDto>
)

internal fun TrendingResponseDto.toDomain() =
    TrendingResponse(
        totalCount = totalCount,
        items = items.map { it.toDomain() }
    )
