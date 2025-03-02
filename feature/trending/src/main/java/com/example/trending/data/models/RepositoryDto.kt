package com.example.trending.data.models

import com.example.trending.domain.models.TrendingRepository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal data class RepositoryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("name")
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("private")
    val private: Boolean,
    @SerialName("owner")
    val owner: OwnerDto,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("fork")
    val fork: Boolean,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
    @SerialName("language")
    val language: String? = null,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("open_issues_count")
    val openIssuesCount: Int,
    @SerialName("license")
    val license: LicenseDto? = null,
    @SerialName("topics")
    val topics: List<String> = emptyList(),
    @SerialName("default_branch")
    val defaultBranch: String,
    @SerialName("score")
    val score: Double? = null,
    @SerialName("updated_at")
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
    @SerialName("created_at")
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant
)

internal fun RepositoryDto.toDomain() =
    TrendingRepository(
        id = id,
        name = name,
        fullName = fullName,
        license = license?.name,
        description = description.orEmpty(),
        language = language.orEmpty(),
        stars = stargazersCount,
        watchers = watchersCount,
        openIssuesCount = openIssuesCount,
        forks = forksCount,
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
