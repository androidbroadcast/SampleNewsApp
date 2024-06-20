package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PrintLogger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import dev.androidbroadcast.news.database.instantiateNewsRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

/**
 * Koin Module with target platform specifics dependencies
 */
internal actual val targetKoinModule: Module = module {
    factory<RoomDatabase.Builder<NewsRoomDatabase>> {
        Room.databaseBuilder<NewsRoomDatabase>(
            name = "${NSHomeDirectory()}/news.db",
            factory =  { instantiateNewsRoomDatabase() }
        ).setDriver(BundledSQLiteDriver())
    }

    factory<Logger> {
        PrintLogger()
    }
}
