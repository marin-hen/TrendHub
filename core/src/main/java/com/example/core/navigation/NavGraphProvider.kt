package com.example.core.navigation

import androidx.navigation.NavGraphBuilder

interface NavGraphProvider {
    fun provideGraph(graphBuilder: NavGraphBuilder)
}