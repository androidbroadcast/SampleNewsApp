package dev.androidbroadcast.newssearchapp

import android.app.Application
import coil3.SingletonImageLoader
import dev.androidbroadcast.news.core.NewsAppPlatform

class NewsApplication @JvmOverloads constructor(
    private val platform: NewsAppPlatform = NewsAppPlatform()
) : Application(), SingletonImageLoader.Factory by platform {

    override fun onCreate() {
        super.onCreate()
        platform.start(
            debug = BuildConfig.DEBUG,
            newsApiKey = BuildConfig.NEWS_API_KEY,
            newsApiBaseUrl = BuildConfig.NEWS_API_BASE_URL,
            platformContext = this
        )
    }
}
