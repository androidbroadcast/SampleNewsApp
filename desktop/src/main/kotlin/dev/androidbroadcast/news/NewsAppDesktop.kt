package dev.androidbroadcast.news

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.androidbroadcast.news.compose.NewsApp
import dev.androidbroadcast.news.core.NewsAppPlatform

fun main() {
    val platform = NewsAppPlatform()
    platform.start(
        debug = true,
        newsApiKey = "155ae65d7264461397c901103488c01e",
        newsApiBaseUrl = "https://newsapi.org/v2/"
    )

    application {
        Window(onCloseRequest = ::exitApplication) {
            NewsApp()
        }
    }
}
