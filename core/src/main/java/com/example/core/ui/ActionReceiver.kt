package com.example.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

typealias ActionReceiver<Action> = (Action) -> Unit

val LocalActionReceiver = staticCompositionLocalOf<ActionReceiver<ViewAction>> {
    error("LocalActionReceiver is not provided")
}

@Composable
inline fun <reified A : ViewAction> currentActionReceiver(): ActionReceiver<A> {
    return LocalActionReceiver.current
}