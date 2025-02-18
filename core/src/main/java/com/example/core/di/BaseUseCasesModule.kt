package com.example.core.di

import com.example.core.usecase.DefaultExceptionMapper
import com.example.core.usecase.DefaultExceptionsResolver
import com.example.core.usecase.ExceptionsMapper
import com.example.core.usecase.ExceptionsResolver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BaseUseCasesModule {

    @Binds
    @Singleton
    abstract fun bindExceptionsMapper(impl: DefaultExceptionMapper): ExceptionsMapper

    @Binds
    @Singleton
    abstract fun bindExceptionsResolver(impl: DefaultExceptionsResolver): ExceptionsResolver
}