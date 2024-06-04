package dev.androidbroadcast.news.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.androidbroadcast.news.database.models.ArticleDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    suspend fun getAll(): List<ArticleDBO>

    @Query("SELECT * FROM articles")
    fun pagingAll(): PagingSource<Int, ArticleDBO>

    @Query("SELECT * FROM articles")
    fun observeAll(): Flow<List<ArticleDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<ArticleDBO>)

    @Delete
    suspend fun remove(articles: List<ArticleDBO>)

    @Query("DELETE FROM articles")
    suspend fun clean()

    @Transaction
    suspend fun cleanAndInsert(articles: List<ArticleDBO>) {
        // Anything inside this method runs in a single transaction.
        clean()
        insert(articles)
    }
}
