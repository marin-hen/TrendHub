package com.example.trending.data

import com.example.trending.domain.usecase.TrendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class DataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindTrendingRepository(impl: TrendingRepositoryImpl): TrendingRepository
}