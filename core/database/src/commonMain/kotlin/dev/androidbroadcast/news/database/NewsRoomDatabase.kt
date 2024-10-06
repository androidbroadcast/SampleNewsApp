package dev.androidbroadcast.news.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import dev.androidbroadcast.news.database.dao.ArticleDao
import dev.androidbroadcast.news.database.models.ArticleDBO
import dev.androidbroadcast.news.database.utils.Converters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class NewsDatabase internal constructor(private val database: NewsRoomDatabase) {
    val articlesDao: ArticleDao
        get() = database.articlesDao()
}

@Database(entities = [ArticleDBO::class], version = 2)
@TypeConverters(Converters::class)
@ConstructedBy(NewsRoomDatabaseConstructor::class)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticleDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NewsRoomDatabaseConstructor : RoomDatabaseConstructor<NewsRoomDatabase> {
    override fun initialize(): NewsRoomDatabase
}

fun NewsDatabase(
    databaseBuilder: RoomDatabase.Builder<NewsRoomDatabase>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
): NewsDatabase {
    return NewsDatabase(
        databaseBuilder
            .setQueryCoroutineContext(dispatcher)
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()
    )
}

public expect fun instantiateNewsRoomDatabase(): NewsRoomDatabase
