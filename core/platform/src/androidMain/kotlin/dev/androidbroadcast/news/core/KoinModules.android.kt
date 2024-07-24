package dev.androidbroadcast.news.core

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.androidbroadcast.common.AndroidLogcatLogger
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

internal actual class TargetKoinDependencies {

    actual fun newsRoomDatabaseBuilder(): RoomDatabase.Builder<NewsRoomDatabase> {
        return Room.databaseBuilder(
            context = KoinPlatform.getKoin().get<Application>(),
            klass = NewsRoomDatabase::class.java,
            name = "news"
        )
    }

    actual fun logger(): Logger = AndroidLogcatLogger()
}
