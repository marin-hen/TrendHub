package com.example.core.designsystem

import androidx.compose.runtime.Stable

@Stable
interface ViewState {
    fun uiError(): UiError? = null
}