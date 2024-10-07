package dev.androidbroadcast.news.core

import dev.androidbroadcast.common.PlatformContext

internal actual fun createAppComponent(
    debuggable: Boolean,
    baseUrl: String,
    apiKey: String,
    platformContext: PlatformContext
): AppComponent {
    return AppComponent::class.create(debuggable, baseUrl, apiKey, platformContext)
}
