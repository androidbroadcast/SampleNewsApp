package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PrintLogger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import java.io.File

internal actual class TargetKoinDependencies {
    actual fun newsRoomDatabaseBuilder(): RoomDatabase.Builder<NewsRoomDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "dev.androidbroadcast.news.db")
        return Room
            .databaseBuilder<NewsRoomDatabase>(name = dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
    }

    actual fun logger(): Logger = PrintLogger()
}
