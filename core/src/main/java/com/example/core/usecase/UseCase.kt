package com.example.core.usecase

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class UseCaseWithParam<Param, Return>(
    private val context: CoroutineContext,
    private val resolver: ExceptionsResolver,
) {
    protected abstract suspend fun execute(param: Param): Return

    suspend operator fun invoke(param: Param): Result<Return> = runCatching {
        withContext(context) { execute(param) }
    }.mapException(resolver::resolveException)
}