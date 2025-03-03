package com.example.core.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun getBooleanFlow(key: String, defaultValue: Boolean = false): Flow<Boolean>
}