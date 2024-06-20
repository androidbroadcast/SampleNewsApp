package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PrintLogger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

/**
 * Koin Module with target platform specifics dependencies
 */
internal actual val targetKoinModule: Module = module {

    factory<RoomDatabase.Builder<NewsRoomDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "dev.androidbroadcast.news.db")
        Room.databaseBuilder<NewsRoomDatabase>(name = dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
    }

    factory<Logger> {
        PrintLogger()
    }
}
