package com.example.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val LocalAppTypography = staticCompositionLocalOf { appTypography() }

internal data class AppTypography(
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle
)

internal fun appTypography() = AppTypography(
    headlineLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    ),
    headlineMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    ),
)

internal fun materialTypography(
    appTypography: AppTypography = appTypography()
) = Typography(
    headlineLarge = appTypography.headlineLarge,
    headlineMedium = appTypography.headlineMedium,
    bodyLarge = appTypography.bodyLarge,
    bodyMedium = appTypography.bodyMedium
)