package com.example.trending.domain.models

data class GetRepositoriesParams(
    val numResultsPerPage: Int = 10,
    val page: Int = 1,
)