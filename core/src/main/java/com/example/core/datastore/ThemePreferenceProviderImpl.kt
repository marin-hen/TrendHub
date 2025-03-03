package com.example.core.datastore

import com.example.core.datastore.DataStoreKeys.KEY_DARK_THEME
import com.example.core.designsystem.ThemePreferenceProvider
import com.example.core.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal class ThemePreferenceProviderImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ThemePreferenceProvider {
    override val isDarkTheme: StateFlow<Boolean> = dataStoreManager.getBooleanFlow(KEY_DARK_THEME)
        .stateIn(
            scope = CoroutineScope(defaultDispatcher + SupervisorJob()),
            started = SharingStarted.Lazily,
            initialValue = false
        )
}