package com.example.trending.domain.models

data class TrendingRepo(
    val id: Long,
    val name: String,
    val description: String,
    val language: String,
    val stars: Int,
    val watchers: Int,
    val forks: Int,
    val ownerName: String,
    val ownerAvatarUrl: String
)