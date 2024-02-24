package dev.androidbroadcast.news.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "articles")
data class ArticleDBO(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @Embedded(prefix = "source.") val source: Source,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("urlToImage") val urlToImage: String,
    @ColumnInfo("publishedAt") val publishedAt: Date,
    @ColumnInfo("content") val content: String,
)

data class Source(
    @ColumnInfo("id") val id: String,
    @ColumnInfo("name") val name: String
)