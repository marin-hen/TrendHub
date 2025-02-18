package com.example.core.exceptions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ApiError(
    val errorCode: String,
    val errorDescription: String,
    val additionalKeys: Map<String, String>? = null
)

@Serializable
internal data class CommonApiError(
    @SerialName("errorCode") val errorCode: String,
    @SerialName("errorDescription") val errorDescription: String,
)