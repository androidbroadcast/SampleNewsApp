package dev.androidbroadcast.news.data.model

import java.util.Date

public data class Article(
    val cacheId: Int = ID_NONE,
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
    val content: String
) {
    public companion object {
        /**
         * Специальный ID для обозначения что ID нету
         */
        public const val ID_NONE: Int = 0
    }
}

public data class Source(
    val id: String,
    val name: String
)
