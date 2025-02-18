package com.example.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.designsystem.ViewState
import com.example.core.designsystem.ui.LocalResources
import com.example.core.designsystem.ui.ScreenResources

@Suppress("UNCHECKED_CAST")
@Composable
fun <State, Action, Resources, ViewModel> Screen(
    viewModel: ViewModel,
    content: @Composable (state: State) -> Unit,
) where State : ViewState,
        Action : ViewAction,
        Resources : ScreenResources,
        ViewModel : BaseViewModelWithResources<State, Action, Resources> {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val resources = remember { viewModel.resources }

    CompositionLocalProvider(
        LocalResources provides resources,
        LocalActionReceiver provides viewModel::onAction as ActionReceiver<ViewAction>
    ) {
        content(state)
        state.uiError()?.let { error ->
            HandleError(error)
        }
    }
}