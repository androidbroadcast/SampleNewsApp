package dev.androidbroadcast.newsapi.models

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
    @SerialName("source") val source: SourceDTO?,
    @SerialName("author") val author: String?,
    @SerialName("title") val title: String?,
    @SerialName("description") val description: String?,
    @SerialName("url") val url: String?,
    @SerialName("urlToImage") val urlToImage: String?,
    @[SerialName("publishedAt") Serializable(with = InstantIso8601Serializer::class)] val publishedAt: Instant?,
    @SerialName("content") val content: String?
)
