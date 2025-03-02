package com.example.trending.domain.models

data class GetRepositoryByNameAndOwnerParams(
    val name: String,
    val owner: String,
)