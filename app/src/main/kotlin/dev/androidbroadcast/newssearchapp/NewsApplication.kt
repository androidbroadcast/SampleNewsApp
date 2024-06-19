package dev.androidbroadcast.newssearchapp

import android.app.Application
import appKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

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
}
