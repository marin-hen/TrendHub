package com.example.core.designsystem.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

interface ScreenResources

val LocalResources = staticCompositionLocalOf<ScreenResources> {
    error("LocalResources not provided")
}

@Composable
inline fun <reified R : ScreenResources> currentResources(): R {
    return LocalResources.current as R
}