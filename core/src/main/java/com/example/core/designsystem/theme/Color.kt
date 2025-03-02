package com.example.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable

val lightColorScheme = AppColors(
    material = lightColorScheme()
)

val darkColorScheme = AppColors(
    material = darkColorScheme()
)

@Immutable
data class AppColors(
    val material: ColorScheme
)