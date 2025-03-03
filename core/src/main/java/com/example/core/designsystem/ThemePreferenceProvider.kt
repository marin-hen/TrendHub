package com.example.core.designsystem

import kotlinx.coroutines.flow.StateFlow

interface ThemePreferenceProvider {
    val isDarkTheme: StateFlow<Boolean>
}