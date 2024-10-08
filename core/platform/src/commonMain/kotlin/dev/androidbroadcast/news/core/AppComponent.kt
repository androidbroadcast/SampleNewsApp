package dev.androidbroadcast.news.core

import androidx.room.RoomDatabase
import dev.androidbroadcast.common.AppDispatchers
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.common.PlatformContext
import dev.androidbroadcast.common.Singleton
import dev.androidbroadcast.news.data.ArticlesRepository
import dev.androidbroadcast.news.database.NewsDatabase
import dev.androidbroadcast.news.database.NewsRoomDatabase
import dev.androidbroadcast.newsapi.NewsApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import kotlin.properties.Delegates.notNull

internal expect fun createAppComponent(
    debuggable: Boolean,
    baseUrl: String,
    apiKey: String,
    platformContext: PlatformContext,
): AppComponent

internal expect fun newsRoomDatabaseBuilder(context: PlatformContext): RoomDatabase.Builder<NewsRoomDatabase>

internal expect fun newLogger(): Logger

@Component
@Singleton
public abstract class AppComponent(
    public val debuggable: Boolean,
    protected val baseUrl: String,
    protected val apiKey: String,
    protected val platformContext: PlatformContext,
) {

    public abstract val articlesRepository: ArticlesRepository

    public abstract val newsDatabase: NewsDatabase

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    internal fun providesJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    @Singleton
    @Provides
    internal fun providesNewsApi(
        json: Json,
    ): NewsApi {
        return NewsApi(
            baseUrl = baseUrl,
            apiKey = apiKey,
            json = json
        )
    }

    @Singleton
    @Provides
    internal fun providesAppDispatchers(): AppDispatchers = AppDispatchers()

    @Singleton
    @Provides
    internal fun providesNewsDatabase(
        dispatchers: AppDispatchers,
    ): NewsDatabase {
        return NewsDatabase(
            databaseBuilder = newsRoomDatabaseBuilder(platformContext),
            dispatcher = dispatchers.io
        )
    }

    @Singleton
    @Provides
    internal fun providesLogger(): Logger = newLogger()

    public companion object {

        public var appComponent: AppComponent by notNull()
            private set

        internal fun create(
            debuggable: Boolean,
            baseUrl: String,
            apiKey: String,
            platformContext: PlatformContext,
        ): AppComponent {
            appComponent = createAppComponent(debuggable, baseUrl, apiKey, platformContext)
            return appComponent
        }
    }
}
