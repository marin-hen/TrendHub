package com.example.core.usecase

import com.example.core.models.BusinessException

internal fun <T> Result<T>.mapException(mapper: (Throwable) -> BusinessException): Result<T> {
    val exception = exceptionOrNull() ?: return this
    return Result.failure(mapper(exception))
}