package com.example.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.core.designsystem.theme.TrendHubTheme
import com.example.core.designsystem.ViewState
import com.example.core.designsystem.ui.LocalResources
import com.example.core.designsystem.ui.ScreenResources

@Composable
fun <V : ViewState, A : ViewAction> TrendHubPreview(
    resources: ScreenResources,
    state: V,
    action: (A) -> Unit = {},
    content: @Composable (V) -> Unit
) {
    TrendHubTheme {
        CompositionLocalProvider(
            LocalResources provides resources,
            LocalActionReceiver provides action as ActionReceiver<ViewAction>
        ) {
            content(state)
        }
    }
}