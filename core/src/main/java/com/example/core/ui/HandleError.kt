package com.example.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.core.designsystem.UiError
import kotlinx.coroutines.launch


@Composable
fun HandleError(uiError: UiError? = null) {
    uiError?.let {
        when (it) {
            is UiError.SnackBarError -> SnackBar(it)
        }
    }
}

@Composable
private fun SnackBar(error: UiError.SnackBarError) {
    val snackBarHostState = remember { SnackbarHostState() }
    var isVisible by remember(error) { mutableStateOf(true) }

    if (isVisible) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(Float.MAX_VALUE)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    snackbarData = it
                )
            }
            Spacer(Modifier.height(100.dp))
        }
        val actionLabel = error.actionButton.value().uppercase()
        val message = error.description.value()

        LaunchedEffect(error) {
            launch {
                val isActionNeeded = error.onAction != null
                val result = snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = if (isActionNeeded) actionLabel else null,
                    withDismissAction = isActionNeeded.not() || error.duration == SnackbarDuration.Indefinite,
                    duration = error.duration
                )

                when (result) {
                    ActionPerformed -> {
                        error.onAction?.invoke()
                        isVisible = false
                    }

                    Dismissed -> {
                        error.onDismiss?.invoke()
                        isVisible = false
                    }
                }
            }
        }
    }
}