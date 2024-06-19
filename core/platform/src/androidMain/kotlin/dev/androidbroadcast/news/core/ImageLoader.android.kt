package dev.androidbroadcast.news.core

import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.disk.directory

internal actual fun newDiskCache(context: PlatformContext): DiskCache? {
    return DiskCache.Builder()
        .directory(context.cacheDir.resolve("images"))
        .maxSizePercent(0.02)
        .minimumMaxSizeBytes(10 * 1024 * 1024) // 10 MBytes
        .build()
}
