import androidx.compose.ui.window.ComposeUIViewController
import dev.androidbroadcast.news.compose.NewsApp
import dev.androidbroadcast.news.core.NewsAppPlatform
import platform.UIKit.UIViewController

@Suppress("unused", "FunctionName")
fun MainViewController(): UIViewController {
    val appPlatform = NewsAppPlatform()
    appPlatform.start(
        debug = true,
        newsApiKey = "155ae65d7264461397c901103488c01e",
        newsApiBaseUrl = "https://newsapi.org/v2/"
    )
    return ComposeUIViewController { NewsApp() }
}
