package com.example.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DataStoreManagerImpl @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val dataStore: DataStore<Preferences>
) : DataStoreManager {


    override suspend fun saveBoolean(key: String, value: Boolean) =
        saveValue(booleanPreferencesKey(key), value)

    private suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        withContext(dispatcher) {
            dataStore.edit { preferences -> preferences[key] = value }
        }
    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        getValue(booleanPreferencesKey(key), defaultValue)

    override fun getBooleanFlow(key: String, defaultValue: Boolean) =
        dataStore.data
            .catch { error ->
                if (error is IOException) {
                    emit(emptyPreferences())
                }
            }
            .map { preferences ->
                preferences[booleanPreferencesKey(key)] ?: defaultValue
            }
            .flowOn(dispatcher)

    private suspend fun <T> getValue(key: Preferences.Key<T>, defaultValue: T) = dataStore.data
        .catch { error ->
            if (error is IOException) {
                emit(emptyPreferences())
            }
        }
        .map {
            it[key] ?: defaultValue
        }
        .flowOn(dispatcher)
        .first()
}