package com.example.core.ui

import androidx.compose.material3.SnackbarDuration
import com.example.core.R
import com.example.core.designsystem.theme.Localizer
import com.example.core.designsystem.UiError
import com.example.core.models.NoInternetConnection

fun Throwable.asAlert(
    description: Localizer? = null,
    actionButton: Localizer = Localizer.Res(R.string.retry),
    duration: SnackbarDuration = SnackbarDuration.Short,
    onAction: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
): UiError.SnackBarError = when (this) {
    is NoInternetConnection -> UiError.SnackBarError(
        description = Localizer.Res(R.string.no_internet_error),
        actionButton = Localizer.Res(R.string.retry),
        duration = SnackbarDuration.Short,
        onAction = onAction,
        onDismiss = onDismiss
    )

    else -> description?.let {
        UiError.SnackBarError(
            description = it,
            actionButton = actionButton,
            duration = duration,
            onAction = onAction,
            onDismiss = onDismiss
        )
    } ?: UiError.SnackBarError(
        description = Localizer.Res(R.string.something_went_wrong_error),
        actionButton = Localizer.Res(R.string.retry),
        duration = SnackbarDuration.Short,
        onAction = onAction,
        onDismiss = onDismiss
    )
}