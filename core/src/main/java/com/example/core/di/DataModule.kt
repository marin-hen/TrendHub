package com.example.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.core.datastore.DataStoreManager
import com.example.core.datastore.DataStoreManagerImpl
import com.example.core.network.ApiManager
import com.example.core.network.NetworkExceptionMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("app_preferences") }
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(impl: DataStoreManagerImpl): DataStoreManager = impl
}