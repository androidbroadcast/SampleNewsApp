package dev.androidbroadcast.news.core

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import org.koin.core.component.KoinComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.scope.Scope
import org.koin.dsl.KoinAppDeclaration

public class NewsAppPlatform :
    KoinComponent,
    KoinScopeComponent,
    SingletonImageLoader.Factory {
    override val scope: Scope
        // Root scope id taken from Koin source code
        get() = getKoin().getScope("_root_")

    public fun start(
        debug: Boolean,
        newsApiKey: String,
        newsApiBaseUrl: String,
        targetAppDeclaration: KoinAppDeclaration = {}
    ) {
        startKoin {
            modules(
                appKoinModule,
                targetKoinModule
            )

            properties(
                mapOf(
                    ConfigProperties.NewsPlatform.Debug to debug,
                    ConfigProperties.NewsApi.ApiKey to newsApiKey,
                    ConfigProperties.NewsApi.BaseUrl to newsApiBaseUrl
                )
            )

            if (debug) {
                printLogger(Level.DEBUG)
            }

            targetAppDeclaration()
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        newImageLoader(
            context,
            debug = getKoin().getProperty(ConfigProperties.NewsPlatform.Debug, false)
        )
}
