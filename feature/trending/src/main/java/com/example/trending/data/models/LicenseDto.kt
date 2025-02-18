package com.example.trending.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LicenseDto(
    @SerialName("key")
    val key: String,
    @SerialName("name")
    val name: String,
    @SerialName("spdx_id")
    val spdxId: String,
    @SerialName("url")
    val url: String? = null,
    @SerialName("node_id")
    val nodeId: String
)