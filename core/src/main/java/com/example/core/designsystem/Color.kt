package com.example.core.designsystem

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf

val lightColorScheme = AppColors(
    material = lightColorScheme()
)

val darkColorScheme = AppColors(
    material = darkColorScheme()
)

internal val LocalAppColors = staticCompositionLocalOf<AppColors> { error("No AppColors provided") }

data class AppColors(
    val material: ColorScheme
)