package dev.androidbroadcast.newssearchapp

import android.app.Application
import appKoinModule
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dev.androidbroadcast.common.newImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(appKoinModule)
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = newImageLoader(context, BuildConfig.DEBUG)

}
