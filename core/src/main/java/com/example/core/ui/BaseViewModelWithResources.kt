package com.example.core.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.designsystem.ViewState
import com.example.core.designsystem.ui.ScreenResources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface ViewAction

abstract class BaseViewModelWithResources<V : ViewState, A : ViewAction, R : ScreenResources>(
    initialState: () -> V,
    protected val savedStateHandle: SavedStateHandle,
    val resources: R
) : ViewModel() {

    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<V> = _state.asStateFlow()

    private val _actions = MutableSharedFlow<A>(extraBufferCapacity = 1)

    init {
        observeActions()
    }

    abstract suspend fun handleActions(action: A)

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            this.block()
        }
    }

    private fun observeActions() {
        _actions
            .filterNotNull()
            .onEach { action ->
                handleActions(action)
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: A) {
        _actions.tryEmit(action)
    }

    protected fun update(block: (V) -> V) {
        _state.value = block(_state.value)
    }
}