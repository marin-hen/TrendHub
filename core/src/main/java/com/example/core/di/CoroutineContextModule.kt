package com.example.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IOContext

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineContextModule {

    @Provides
    @Singleton
    @DefaultContext
    fun defaultContext(): CoroutineContext = Dispatchers.Default + CoroutineName("Default")

    @Provides
    @Singleton
    @IOContext
    fun ioContext(): CoroutineContext = Dispatchers.IO + CoroutineName("IO")
}