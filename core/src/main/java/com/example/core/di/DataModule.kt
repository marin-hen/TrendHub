package com.example.core.di

import com.example.core.network.ApiManager
import com.example.core.network.NetworkExceptionMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    fun provideApiManager(retrofit: Retrofit, mapper: NetworkExceptionMapper): ApiManager {
        return ApiManager(retrofit, mapper)
    }
}