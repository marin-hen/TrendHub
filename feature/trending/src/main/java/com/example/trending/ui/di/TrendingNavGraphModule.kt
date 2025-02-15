package com.example.trending.ui.di

import com.example.core.navigation.NavGraphProvider
import com.example.trending.ui.navigation.TrendingNavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TrendingNavGraphModule {
    @Binds
    @IntoSet
    abstract fun bindsTrendingNavGraph(impl: TrendingNavGraphProvider): NavGraphProvider
}