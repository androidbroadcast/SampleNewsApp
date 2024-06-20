package dev.androidbroadcast.news

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun platformThemeSetup(darkTheme: Boolean, colorScheme: ColorScheme) {
}

@Composable
internal actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme {
    TODO("Not yet implemented")
}

internal actual fun isPlatformWithDynamicSystemTheme(): Boolean {
    TODO("Not yet implemented")
}
