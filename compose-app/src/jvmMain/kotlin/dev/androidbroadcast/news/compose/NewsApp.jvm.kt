package dev.androidbroadcast.news.compose

import androidx.compose.runtime.Composable

@Composable
internal actual fun NewsPlatformTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    content()
}
