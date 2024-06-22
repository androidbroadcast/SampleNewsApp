package dev.androidbroadcast.news

import android.app.Activity
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
internal actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme {
    if (!isPlatformWithDynamicSystemTheme()) error("Dynamic theme is supported only on Android S+")
    val context = LocalContext.current
    return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
}

internal actual fun isPlatformWithDynamicSystemTheme(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
