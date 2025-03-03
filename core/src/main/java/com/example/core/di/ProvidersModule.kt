package com.example.core.di

import com.example.core.datastore.ThemePreferenceProviderImpl
import com.example.core.designsystem.ThemePreferenceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ProvidersModule {
    @Binds
    @Singleton
    abstract fun bindThemePreferenceProvider(impl: ThemePreferenceProviderImpl): ThemePreferenceProvider
}