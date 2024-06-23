package dev.androidbroadcast.news.core

import coil3.PlatformContext
import coil3.disk.DiskCache
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal actual fun newDiskCache(context: PlatformContext): DiskCache? {
    @Suppress("UNCHECKED_CAST")
    val paths: List<NSURL> = NSFileManager.defaultManager.URLsForDirectory(NSCachesDirectory, NSUserDomainMask) as List<NSURL>
    val cacheDirectory = paths.firstOrNull()?.path
    return DiskCache
        .Builder()
        .apply { if (cacheDirectory != null) directory(cacheDirectory.toPath()) }
        .maxSizePercent(0.02)
        .minimumMaxSizeBytes(10 * 1024 * 1024) // 10 MBytes
        .build()
}
