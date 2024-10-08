package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.androidbroadcast.common.AndroidLogcatLogger
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PlatformContext
import dev.androidbroadcast.news.database.NewsRoomDatabase

internal actual fun newsRoomDatabaseBuilder(context: PlatformContext): RoomDatabase.Builder<NewsRoomDatabase> {
    return Room.databaseBuilder(
        context = context,
        klass = NewsRoomDatabase::class.java,
        name = "news"
    )
}

internal actual fun newLogger(): Logger = AndroidLogcatLogger()

internal actual fun createAppComponent(
    debuggable: Boolean,
    baseUrl: String,
    apiKey: String,
    platformContext: PlatformContext
): AppComponent {
    return AppComponent::class.create(debuggable, baseUrl, apiKey, platformContext)
}
