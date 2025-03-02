package com.example.trending.domain.models

import java.time.Instant

data class TrendingRepository(
    val id: Long,
    val name: String,
    val license: String?,
    val fullName: String,
    val description: String,
    val language: String,
    val stars: Int,
    val forks: Int,
    val openIssuesCount: Int,
    val watchers: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val updatedAt: Instant,
    val createdAt: Instant,
)