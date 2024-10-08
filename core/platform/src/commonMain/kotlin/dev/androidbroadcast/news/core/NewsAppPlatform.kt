package dev.androidbroadcast.news.core

import coil3.ImageLoader
import coil3.SingletonImageLoader
import dev.androidbroadcast.common.PlatformContext
import kotlin.properties.Delegates.notNull

public class NewsAppPlatform :
    SingletonImageLoader.Factory {

    public var appComponent: AppComponent by notNull()
        private set

    public fun start(
        debug: Boolean,
        newsApiKey: String,
        newsApiBaseUrl: String,
        platformContext: PlatformContext,
    ) {
        appComponent = AppComponent.create(
            debug, newsApiBaseUrl, newsApiKey,
            platformContext
        )
    }

    override fun newImageLoader(context: coil3.PlatformContext): ImageLoader {
        return newImageLoader(context)
    }
}
