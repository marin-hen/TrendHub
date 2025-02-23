package com.example.core.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer

internal const val TWEEN_VALUE = 1000
internal const val PREF_ALPHA_VALUE = 0.99f
internal const val INITIAL_VALUE = 0f
internal const val TARGET_VALUE = 500f

fun Modifier.shimmerEffect(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val translateAnimation by infiniteTransition.animateFloat(
        initialValue = INITIAL_VALUE,
        targetValue = TARGET_VALUE,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = TWEEN_VALUE, easing = LinearEasing)
        ),
    )

    val neutralLightColor = MaterialTheme.colorScheme.surface
    val neutralMediumColor = MaterialTheme.colorScheme.surfaceVariant

    graphicsLayer {
        alpha = PREF_ALPHA_VALUE
    }
        .drawWithCache {
            val shimmerBrush = Brush.linearGradient(
                colors = listOf(
                    neutralLightColor,
                    neutralMediumColor,
                    neutralLightColor
                ),
                start = Offset(translateAnimation, 0f),
                end = Offset(translateAnimation + size.width, size.height)
            )
            onDrawBehind {
                drawRect(brush = shimmerBrush)
            }
        }
}