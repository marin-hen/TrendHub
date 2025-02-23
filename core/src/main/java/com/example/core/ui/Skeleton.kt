package com.example.core.ui

import androidx.compose.runtime.compositionLocalOf

data class Skeleton(
    val isLoading: Boolean = false
)

val LocalSkeleton = compositionLocalOf { Skeleton() }