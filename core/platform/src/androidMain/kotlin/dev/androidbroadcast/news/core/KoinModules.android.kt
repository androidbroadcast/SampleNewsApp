package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.androidbroadcast.common.AndroidLogcatLogger
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val targetKoinModule: Module = module {

    factory<RoomDatabase.Builder<NewsRoomDatabase>> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = NewsRoomDatabase::class.java,
            name = "news"
        )
    }

    factory<Logger> {
        AndroidLogcatLogger()
    }
}
