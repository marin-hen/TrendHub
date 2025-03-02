package com.example.core.designsystem

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Stable
import com.example.core.designsystem.theme.Localizer

@Stable
sealed interface UiError {
    data class SnackBarError(
        val description: Localizer,
        val actionButton: Localizer,
        val duration: SnackbarDuration,
        val onAction: (() -> Unit)? = null,
        val onDismiss: (() -> Unit)? = null,
    ) : UiError
}