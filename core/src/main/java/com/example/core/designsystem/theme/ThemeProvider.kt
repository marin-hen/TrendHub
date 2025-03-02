package com.example.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

object ThemeProvider {
    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val dimens: Dimensions
        @Composable get() = LocalAppDimens.current

    val colors: AppColors
        @Composable get() = LocalAppColors.current
}

internal val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided")
}

internal val LocalAppDimens = staticCompositionLocalOf<Dimensions> {
    error("No dimensions provided")
}

internal val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("No font is provided")
}