package dev.androidbroadcast.news.core

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.news.database.NewsRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

/**
 * Koin Module with target platform specifics dependencies
 */
internal actual val targetKoinModule: Module = module {
    factory<RoomDatabase.Builder<NewsRoomDatabase>> {
        val dbFilePath = NSHomeDirectory() + "/my_room.db"
        Room.databaseBuilder<NewsRoomDatabase>(
            name = dbFilePath,
            factory =  { NewsRoomDatabase::class.instantiateImpl() }
        )
    }

    factory<Logger> {
        object : Logger {
            override fun d(tag: String, message: String) {
            }

            override fun e(tag: String, message: String) {
            }
        }
    }
}
