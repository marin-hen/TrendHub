package com.example.core.usecase

import com.example.core.models.BusinessException
import com.example.core.models.TechnicalException
import javax.inject.Inject

interface ExceptionsResolver {
    fun resolveException(exception: Throwable): BusinessException
}

class DefaultExceptionsResolver @Inject constructor(
    private val mapper: ExceptionsMapper,
) : ExceptionsResolver {
    override fun resolveException(exception: Throwable): BusinessException {
        return when (val mappedException = mapper.map(exception)) {
            is BusinessException -> mappedException
            is TechnicalException -> {
                BusinessException.BlockingException(
                    cause = mappedException,
                    id = mappedException.code.toString(),
                    message = mappedException.message
                )
            }
        }
    }
}