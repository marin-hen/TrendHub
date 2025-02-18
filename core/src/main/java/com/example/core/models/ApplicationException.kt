package com.example.core.models

sealed class ApplicationException(
    override val message: String?,
    override val cause: Throwable?,
) : Throwable(message = message, cause = cause)

class TechnicalException(
    cause: Throwable?,
    message: String? = null,
    val code: Int,
    val details: TechnicalExceptionDetails? = null
) : ApplicationException(cause = cause, message = message) {

    data class TechnicalExceptionDetails(
        val errorCode: String,
        val errorDescription: String,
        val additionalKeys: Map<String, String>?
    )
}

sealed class BusinessException(
    cause: Throwable?,
    val id: String? = null,
    message: String? = null,
) : ApplicationException(cause = cause, message = message) {

    open class BlockingException(
        cause: Throwable?,
        id: String? = null,
        message: String? = null,
    ) : BusinessException(cause, id, message)

    open class WarningException(
        cause: Throwable?,
        id: String? = null,
        message: String? = null,
    ) : BusinessException(cause, id, message)
}