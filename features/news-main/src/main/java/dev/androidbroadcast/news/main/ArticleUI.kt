package dev.androidbroadcast.news.main

internal data class ArticleUI(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val url: String
)
