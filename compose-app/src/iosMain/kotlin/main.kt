//import com.onixcamera.cams_list.api.CamsListApi
import androidx.compose.ui.window.ComposeUIViewController
import dev.androidbroadcast.news.compose.NewsApp
import platform.UIKit.UIViewController

public fun MainViewController(): UIViewController {

    return ComposeUIViewController {
        NewsApp()
    }
}
