package com.example.core.designsystem

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import kotlinx.serialization.Serializable

@Stable
@Serializable
sealed class Localizer {

    @Serializable
    data class Res(@StringRes val resId: Int) : Localizer() {
        @Composable
        override fun value(vararg params: String): String = stringResource(resId, *params)
    }

    @Serializable
    data class Text(val text: String) : Localizer() {
        @Composable
        override fun value(vararg params: String): String =
            if (params.isNotEmpty()) text.format(*params) else text
    }

    @Composable
    abstract fun value(vararg params: String): String
}