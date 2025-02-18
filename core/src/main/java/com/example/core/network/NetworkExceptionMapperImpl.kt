package com.example.core.network

import com.example.core.exceptions.ApiError
import com.example.core.exceptions.CommonApiError
import com.example.core.exceptions.NetworkException
import com.example.core.exceptions.NetworkException.TimeOutException
import com.example.core.exceptions.NetworkException.NoInternetConnection
import com.example.core.exceptions.NetworkException.UnknownException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

interface NetworkExceptionMapper {
    fun map(throwable: Throwable): Throwable
}

class NetworkExceptionMapperImpl @Inject constructor(
    private val json: Json
) : NetworkExceptionMapper {
    override fun map(throwable: Throwable): Throwable = when (throwable) {
        is IOException -> handleNetworkExceptions(throwable)
        is HttpException -> handleApiError(throwable)
        else -> UnknownException(null, throwable)
    }

    private fun handleNetworkExceptions(exception: IOException) = when (exception) {
        is UnknownHostException, is ConnectException -> NoInternetConnection(cause = exception)
        is SocketTimeoutException -> TimeOutException(cause = exception)
        else -> UnknownException(message = null, cause = exception)
    }

    private fun handleApiError(exception: HttpException): NetworkException {
        val response = exception.response()
        val errorJson = response?.errorBody()?.string()?.takeIf { it.isNotEmpty() }


        val apiErrorResult = runCatching {
            errorJson?.let {
                try {
                    with(json.decodeFromString(CommonApiError.serializer(), it)) {
                        ApiError(
                            errorCode = errorCode,
                            errorDescription = errorDescription
                        )
                    }
                } catch (_: Exception) {
                    ApiError(
                        errorCode = UNKNOWN_ERROR_CODE,
                        errorDescription = it
                    )
                }
            }
        }

        val apiError = apiErrorResult.getOrNull()
        val message = apiError?.errorDescription ?: errorJson ?: response?.message()
        ?: ERROR_IS_ABSENT

        return exception.code().handleApiErrors(
            message = message,
            cause = apiErrorResult.exceptionOrNull(),
            apiError = apiError
        )
    }

    private fun Int.handleApiErrors(message: String, cause: Throwable?, apiError: ApiError?) =
        when (this) {
            HTTP_INTERNAL_ERROR -> NetworkException.InternalErrorException(message, cause, apiError)
            else -> UnknownException(message, cause, apiError)
        }

    private companion object {
        const val UNKNOWN_ERROR_CODE = "UNKNOWN_ERROR_CODE"
        const val ERROR_IS_ABSENT = "ERROR_IS_ABSENT"
    }
}