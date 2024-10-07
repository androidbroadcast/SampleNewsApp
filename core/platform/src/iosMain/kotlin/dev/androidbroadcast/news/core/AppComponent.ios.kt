package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PlatformContext
import dev.androidbroadcast.common.PrintLogger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import dev.androidbroadcast.news.database.instantiateNewsRoomDatabase
import platform.Foundation.NSHomeDirectory

internal actual fun newsRoomDatabaseBuilder(context: PlatformContext): RoomDatabase.Builder<NewsRoomDatabase> {
    return Room.databaseBuilder<NewsRoomDatabase>(
        name = "${NSHomeDirectory()}/news.db",
        factory =  { instantiateNewsRoomDatabase() }
    ).setDriver(BundledSQLiteDriver())
}

internal actual fun newLogger(): Logger = PrintLogger()
