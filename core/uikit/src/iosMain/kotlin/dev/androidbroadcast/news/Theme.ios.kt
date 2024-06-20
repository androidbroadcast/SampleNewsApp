package dev.androidbroadcast.news

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme {
    error("Dynamic theming isn't supported")
}

internal actual fun isPlatformWithDynamicSystemTheme(): Boolean = false

@Composable
internal actual fun platformThemeSetup(
    darkTheme: Boolean,
    colorScheme: ColorScheme,
) {
    // Do nothing
}
