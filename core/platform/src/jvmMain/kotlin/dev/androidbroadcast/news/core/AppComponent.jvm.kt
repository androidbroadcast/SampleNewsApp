package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PlatformContext
import dev.androidbroadcast.common.PrintLogger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import java.io.File

internal actual fun newsRoomDatabaseBuilder(context: PlatformContext): RoomDatabase.Builder<NewsRoomDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "dev.androidbroadcast.news.db")
    return Room.databaseBuilder<NewsRoomDatabase>(name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
}

internal actual fun newLogger(): Logger = PrintLogger()

internal actual fun createAppComponent(
    debuggable: Boolean,
    baseUrl: String,
    apiKey: String,
    platformContext: PlatformContext
): AppComponent {
    return AppComponent::class.create(debuggable, baseUrl, apiKey, platformContext)
}
