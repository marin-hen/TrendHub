package com.example.trending.ui.di

import com.example.trending.ui.delegates.TrendingNavigationDelegate
import com.example.trending.ui.delegates.TrendingNavigationDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class TrendingDelegatesModule {
    @Binds
    @ViewModelScoped
    abstract fun bindNavigationDelegate(impl: TrendingNavigationDelegateImpl): TrendingNavigationDelegate
}