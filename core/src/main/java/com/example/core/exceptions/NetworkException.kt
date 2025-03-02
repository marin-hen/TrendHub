package com.example.core.exceptions

import java.net.HttpURLConnection

sealed class NetworkException(
    message: String? = null,
    cause: Throwable? = null,
    val code: Int,
    val error: ApiError? = null
) : Throwable(message = message, cause = cause) {

    class InternalErrorException(
        message: String?,
        cause: Throwable?,
        error: ApiError? = null,
    ) : NetworkException(
        code = HttpURLConnection.HTTP_INTERNAL_ERROR,
        message = message,
        cause = cause,
        error = error
    )

    class ForbiddenException(
        message: String?,
        cause: Throwable?,
        error: ApiError? = null,
    ) : NetworkException(
        code = HttpURLConnection.HTTP_FORBIDDEN,
        message = message,
        cause = cause,
        error = error
    )

    class UnknownException(
        message: String?,
        cause: Throwable?,
        error: ApiError? = null,
    ) : NetworkException(
        code = CODE_UNKNOWN_ERROR,
        message = message,
        cause = cause,
        error = error
    )

    class NoInternetConnection(cause: Throwable?) : NetworkException(
        code = CODE_NO_INTERNET,
        cause = cause
    )

    class TimeOutException(cause: Throwable?) : NetworkException(
        code = CODE_TIMEOUT,
        cause = cause,
    )

    companion object {
        const val CODE_UNKNOWN_ERROR = -1
        const val CODE_NO_INTERNET = 0
        const val CODE_TIMEOUT = 1
    }
}