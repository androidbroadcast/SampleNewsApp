package dev.androidbroadcast.news.core

import androidx.room.RoomDatabase
import dev.androidbroadcast.common.AppDispatchers
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.news.data.ArticlesRepository
import dev.androidbroadcast.news.database.NewsDatabase
import dev.androidbroadcast.news.database.NewsRoomDatabase
import dev.androidbroadcast.newsapi.NewsApi
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal expect class TargetKoinDependencies() {
    fun newsRoomDatabaseBuilder(): RoomDatabase.Builder<NewsRoomDatabase>

    fun logger(): Logger
}

internal val targetKoinModule: Module = module {

    val targetKoinDependencies = TargetKoinDependencies()

    factory { targetKoinDependencies.newsRoomDatabaseBuilder() }

    factory { targetKoinDependencies.logger() }
}

internal val appKoinModule: Module =
    module {
        single {
            Json {
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        }

        single {
            NewsApi(
                baseUrl = getProperty(ConfigProperties.NewsApi.BaseUrl),
                apiKey = getProperty(ConfigProperties.NewsApi.ApiKey),
                json = get<Json>()
            )
        }

        single { AppDispatchers() }

        single {
            val dispatchers = get<AppDispatchers>()
            NewsDatabase(
                databaseBuilder = get<RoomDatabase.Builder<NewsRoomDatabase>>(),
                dispatcher = dispatchers.io
            )
        }

        singleOf(::ArticlesRepository)
    }
