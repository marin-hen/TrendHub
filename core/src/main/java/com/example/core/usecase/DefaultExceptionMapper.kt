package com.example.core.usecase

import com.example.core.exceptions.NetworkException
import com.example.core.models.ApplicationException
import com.example.core.models.BusinessException
import com.example.core.models.NoInternetConnection
import com.example.core.models.TechnicalException
import javax.inject.Inject

interface ExceptionsMapper {
    fun map(exception: Throwable): ApplicationException
}

internal class DefaultExceptionMapper @Inject constructor() : ExceptionsMapper {
    override fun map(exception: Throwable): ApplicationException = when {
        exception is BusinessException -> exception
        exception.isNoInternetException() -> NoInternetConnection()
        else -> exception.asTechnicalException()
    }

    private fun Throwable.isNoInternetException() =
        this is NetworkException.NoInternetConnection || this is NetworkException.TimeOutException


    private fun Throwable.asTechnicalException(): TechnicalException = when (this) {
        is TechnicalException -> this
        is NetworkException -> this.asTechnicalException()
        else -> TechnicalException(
            cause = this,
            message = "$UNKNOWN_ERROR_MESSAGE $message",
            code = UNKNOWN_ERROR_CODE,
        )
    }

    private fun NetworkException.asTechnicalException() = TechnicalException(cause = this,
        message = "HTTP_CODE: $code, ErrorCode: ${error?.errorCode ?: "ErrorCode is not defined"}",
        code = code,
        details = error?.let {
            with(it) {
                TechnicalException.TechnicalExceptionDetails(
                    errorCode = errorCode,
                    errorDescription = errorDescription,
                    additionalKeys = additionalKeys
                )
            }
        })

    private companion object {
        const val UNKNOWN_ERROR_CODE = 1000
        const val UNKNOWN_ERROR_MESSAGE = "UNKNOWN_ERROR:"
    }
}