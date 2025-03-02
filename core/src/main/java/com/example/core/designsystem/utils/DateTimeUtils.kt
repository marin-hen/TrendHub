package com.example.core.designsystem.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss"

fun Instant.toDisplayString(): String {
    val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN, Locale.getDefault())
    return this.atZone(ZoneId.systemDefault()).format(formatter)
}