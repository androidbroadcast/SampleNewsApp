package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PrintLogger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import dev.androidbroadcast.news.database.instantiateNewsRoomDatabase
import platform.Foundation.NSHomeDirectory

internal actual class TargetKoinDependencies {
    actual fun newsRoomDatabaseBuilder(): RoomDatabase.Builder<NewsRoomDatabase> {
        return Room.databaseBuilder<NewsRoomDatabase>(
            name = "${NSHomeDirectory()}/news.db",
            factory =  { instantiateNewsRoomDatabase() }
        ).setDriver(BundledSQLiteDriver())
    }

    actual fun logger(): Logger {
        return PrintLogger()
    }
}
