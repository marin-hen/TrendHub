package com.example.trending.domain.models

data class TrendingRepo(
    val id: Long,
    val fullName: String,
    val description: String,
    val language: String,
    val stars: Int,
    val forks: Int,
    val watchers: Int,
    val ownerName: String,
    val ownerAvatarUrl: String
)