import dev.androidbroadcast.common.AndroidLogcatLogger
import dev.androidbroadcast.common.AppDispatchers
import dev.androidbroadcast.common.Logger
import dev.androidbroadcast.news.data.ArticlesRepository
import dev.androidbroadcast.news.database.NewsDatabase
import dev.androidbroadcast.newsapi.NewsApi
import dev.androidbroadcast.newssearchapp.BuildConfig
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val appKoinModule = module {
    factory<Logger> {
        AndroidLogcatLogger()
    }
    
    single {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }
    
    single {
        NewsApi(
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
            apiKey = BuildConfig.NEWS_API_KEY,
            json = get<Json>(),
        )
    }
    
    single { AppDispatchers() }
    
    single {
        val dispatchers = get<AppDispatchers>()
        NewsDatabase(
            applicationContext = androidApplication(),
            dispatcher = dispatchers.io
        )
    }
    
    singleOf(::ArticlesRepository)
}
